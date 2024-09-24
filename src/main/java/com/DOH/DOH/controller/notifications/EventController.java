package com.DOH.DOH.controller.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;
import com.DOH.DOH.service.notifications.EventService;
import com.DOH.DOH.service.user.UserSessionService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("event")
public class EventController {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final EventService eventService;
    private final UserSessionService userSessionService;

    public EventController(EventService eventService, UserSessionService userSessionService) {
        this.eventService = eventService;
        this.userSessionService = userSessionService;
    }

    // 이벤트 목록 조회
    @GetMapping("/list")
    public String eventList(Model model) {
        log.info("이벤트 목록 조회 요청");

        List<EventDTO> eventList = eventService.getEventListLimited();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");

        for (EventDTO event : eventList) {
            if (event.getEventCreateTime() != null) {
                event.setFormattedCreateTime(event.getEventCreateTime().format(formatter));
            }
            if (event.getEventStartDate() != null) {
                event.setFormattedStartDate(event.getEventStartDate().format(formatter));
            }
            if (event.getEventEndDate() != null) {
                event.setFormattedEndDate(event.getEventEndDate().format(formatter));
            }
        }

        model.addAttribute("eventList", eventList);
        log.info("이벤트 목록 조회 완료 - 총 이벤트 수: {}", eventList.size());

        if (isAdminUser()) {
            List<EventDTO> tempEventList = eventService.getTempEventList();
            model.addAttribute("tempEventList", tempEventList);
            log.info("관리자 권한으로 임시 저장된 이벤트 조회 완료 - 총 임시 이벤트 수: {}", tempEventList.size());
        }

        return "notifications/eventList";
    }

    // 이벤트 작성(작성&수정) 페이지 렌더링
    @PostMapping("/admin/write")
    public String eventWriteForm(@RequestParam(value = "eventNum", required = false) Long eventNum, Model model) {
        log.info("이벤트 작성 페이지 요청 - eventNum: {}", eventNum);

        if (eventNum != null) {
            EventDTO eventInfo = eventService.getEventById(eventNum, model);
            model.addAttribute("eventDTO", eventInfo);
            log.info("이벤트 수정 페이지 로드 - eventNum: {}", eventNum);
        } else {
            model.addAttribute("eventDTO", new EventDTO());
            log.info("이벤트 신규 등록 페이지 로드");
        }
        return "notifications/eventWrite";
    }

    //이벤트 등록(작성&수정) 페이지
    @PostMapping("/admin/create")
    public String eventWrite(@ModelAttribute EventDTO eventDTO,
                             @RequestParam(value = "file", required = false) MultipartFile file,
                             @RequestParam("eventCreateTime") String eventCreateTimeStr,
                             @RequestParam("eventStartDate") String eventStartDateStr,
                             @RequestParam("eventEndDate") String eventEndDateStr,
                             RedirectAttributes redirectAttributes) throws Exception {
        log.info("이벤트 작성 요청 - 제목: {}", eventDTO.getEventTitle());

        // 관리자 권한 확인
        if (!isAdminUser()) {
            redirectAttributes.addFlashAttribute("errorMessage", "이벤트 작성 권한이 없습니다.");
            return "redirect:/event/list";
        }

        // 세션에서 userNum 설정
        eventDTO.setUserNum(Math.toIntExact(userSessionService.userNum()));

        // 날짜 변환 로직
        try {
            eventDTO.setEventCreateTime(convertStringToLocalDate(eventCreateTimeStr));
            eventDTO.setEventStartDate(convertStringToLocalDate(eventStartDateStr));
            eventDTO.setEventEndDate(convertStringToLocalDate(eventEndDateStr));
        } catch (Exception e) {
            log.error("날짜 변환 오류: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "날짜 형식이 올바르지 않습니다.");
            return "redirect:/event/list";
        }

        // 기존 이미지 삭제 및 S3 파일 업로드 처리
        if (file != null && !file.isEmpty()) {
            try {
                // 새로운 파일 업로드 전에 기존 이미지 삭제 (수정일 경우)
                if (eventDTO.getEventImageName() != null && !eventDTO.getEventImageName().isEmpty()) {
                    deleteFileFromS3Bucket(eventDTO.getEventImageName()); // 기존 이미지 삭제
                }

                // 새로운 이미지 업로드
                String newFileName = uploadFileToS3Bucket(file);
                eventDTO.setEventImageName(newFileName); // 새로운 파일명을 DTO에 설정

            } catch (IOException e) {
                log.error("S3 파일 업로드 실패: {}", e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "파일 업로드 중 오류가 발생했습니다.");
                return "redirect:/event/list";
            }
        }

        // 이벤트 저장 또는 수정 처리
        if (eventDTO.getEventNum() == null) {
            eventService.eventRegister(eventDTO);  // 이벤트 등록
            log.info("이벤트 등록 완료 - 제목: {}", eventDTO.getEventTitle());
        } else {
            eventService.eventUpdate(eventDTO);  // 이벤트 수정
            log.info("이벤트 수정 완료 - eventNum: {}", eventDTO.getEventNum());
        }

        // 성공 메시지
        redirectAttributes.addFlashAttribute("successMessage", "이벤트가 성공적으로 등록되었습니다.");
        return "redirect:/event/list";
    }

    // 파일 삭제 메서드
    public void deleteFileFromS3Bucket(String eventImageName) {
        try {
            // S3에서 파일 삭제
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, eventImageName));
            System.out.println("파일 삭제 성공: " + eventImageName);
        } catch (Exception e) {
            System.err.println("파일 삭제 실패: " + e.getMessage());
            throw new RuntimeException("S3 파일 삭제 실패", e);
        }
    }

    // 날짜 변환 공통 함수
    private LocalDate convertStringToLocalDate(String dateStr) throws DateTimeParseException {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    // 이벤트 삭제 처리
    @PostMapping("/admin/delete")
    public String deleteEvent(@RequestParam("eventNum") Long eventNum, RedirectAttributes redirectAttributes) {
        log.info("이벤트 삭제 요청 - eventNum: {}", eventNum);

        if (!isAdminUser()) {
            redirectAttributes.addFlashAttribute("errorMessage", "이벤트 삭제 권한이 없습니다.");
            return "redirect:/event/list";
        }

        eventService.deleteEvent(eventNum);
        log.info("이벤트 삭제 완료 - eventNum: {}", eventNum);

        return "redirect:/event/list";
    }

    // S3에 파일 업로드 처리
    private String uploadFileToS3Bucket(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        try (InputStream inputStream = file.getInputStream()) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata);
            amazonS3.putObject(putObjectRequest);
        }
        String fileUrl = amazonS3.getUrl(bucketName, fileName).toString();
        log.info("파일이 S3에 업로드되었습니다. fileUrl: {}", fileUrl);
        return fileUrl;
    }

    // 관리자 권한 확인 메서드
    private boolean isAdminUser() {
        String userEmail = userSessionService.userEmail();
        String userRole = userSessionService.userRole();
        return userEmail.equals("admin") || userRole.equals("ROLE_ADMIN");
    }

    // 이벤트 상세보기 페이지
    @GetMapping("/detail")
    public String viewEvent(@RequestParam("eventNum") Long eventNum, Model model) {
        log.info("이벤트 상세보기 요청 - eventNum: {}", eventNum);

        EventDTO eventInfo = eventService.getEventById(eventNum, model);
        if (eventInfo != null) {
            model.addAttribute("event", eventInfo);
            log.info("이벤트 상세 정보 로드 완료 - 제목: {}", eventInfo.getEventTitle());
        } else {
            log.warn("이벤트 정보가 존재하지 않음 - eventNum: {}", eventNum);
            model.addAttribute("errorMessage", "이벤트 정보를 찾을 수 없습니다.");
        }

        return "notifications/eventView";
    }
}
