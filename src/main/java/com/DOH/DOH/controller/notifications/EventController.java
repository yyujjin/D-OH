package com.DOH.DOH.controller.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;
import com.DOH.DOH.service.notifications.EventService;
import com.DOH.DOH.service.user.UserSessionService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

        // LocalDateTime을 String으로 변환
        for (EventDTO event : eventList) {
            if (event.getEventCreateTime() != null) {
                event.setFormattedCreateTime(event.getEventCreateTime().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));
            }
        }

        model.addAttribute("eventList", eventList);
        log.info("이벤트 목록 조회 완료 - 총 이벤트 수: {}", eventList.size());

        String userRole = userSessionService.userRole();
        if (userRole.equals("ROLE_ADMIN")) {
            List<EventDTO> tempEventList = eventService.getTempEventList();
            model.addAttribute("tempEventList", tempEventList);
            log.info("관리자 권한으로 임시 저장된 이벤트 조회 완료 - 총 임시 이벤트 수: {}", tempEventList.size());
        }
        return "notifications/eventList";
    }

    // 이벤트 작성(작성&수정) 페이지 렌더링
    @PostMapping("/admin/write")
    public String eventWriteForm(@RequestParam(value = "eventNum", required = false) Long eventNum, ModelMap modelMap) throws Exception {
        log.info("이벤트 작성 페이지 요청 - eventNum: {}", eventNum);

        if (eventNum != null) {
            EventDTO eventInfo = eventService.getEventById(eventNum);
            modelMap.addAttribute("eventDTO", eventInfo);
            log.info("이벤트 수정 페이지 로드 - eventNum: {}", eventNum);
        } else {
            modelMap.addAttribute("eventDTO", new EventDTO());
            log.info("이벤트 신규 등록 페이지 로드");
        }
        return "notifications/eventWrite";
    }

    @PostMapping("/admin/create")
    public String eventWrite(@ModelAttribute EventDTO eventDTO,
                             @RequestParam(value = "file", required = false) MultipartFile file,
                             @RequestParam("eventCreateTime") String eventCreateTimeStr, // 날짜를 String으로 받음
                             RedirectAttributes redirectAttributes) throws Exception {
        log.info("이벤트 작성 요청 - 제목: {}", eventDTO.getEventTitle());

        String userEmail = userSessionService.userEmail();
        String userRole = userSessionService.userRole();
        Long userNum = userSessionService.userNum(); // userNum 가져오기

        if (!userEmail.equals("admin") && !userRole.equals("ROLE_ADMIN")) {
            redirectAttributes.addFlashAttribute("errorMessage", "이벤트 작성 권한이 없습니다.");
            log.warn("이벤트 작성 권한 없음 - userEmail: {}, userRole: {}", userEmail, userRole);
            return "redirect:/event/list";
        }

        eventDTO.setUserNum(Math.toIntExact(userNum));

        // 날짜 문자열을 LocalDate로 변환
        LocalDate eventCreateTime = LocalDate.parse(eventCreateTimeStr);
        eventDTO.setEventCreateTime(eventCreateTime); // 변환된 날짜를 DTO에 설정

        // S3에 파일이 업로드된 경우
        if (file != null && !file.isEmpty()) {
            String newFileName = uploadFileToS3Bucket(file);
            eventDTO.setEventImageName(newFileName);
        }
//        if (file != null && !file.isEmpty()) {
//            try {
//                String fileUrl = uploadFileToS3Bucket(file); // S3에 파일 업로드
//                eventDTO.setEventImageUrl(fileUrl); // S3 URL을 eventDTO에 설정
//                eventDTO.setEventImageName(file.getOriginalFilename()); // 파일 이름을 eventDTO에 설정
//                log.info("파일 업로드 완료 - 파일명: {}, S3 URL: {}", file.getOriginalFilename(), fileUrl);
//            } catch (IOException e) {
//                log.error("S3 파일 업로드 실패 - 제목: {}, 오류: {}", eventDTO.getEventTitle(), e.getMessage());
//                redirectAttributes.addFlashAttribute("errorMessage", "파일 업로드 중 오류가 발생했습니다.");
//                return "redirect:/event/list";
//            }
//        }

        // 이벤트 번호가 없으면 등록, 있으면 수정
        if (eventDTO.getEventNum() == null) {
            eventService.eventRegister(eventDTO);
            log.info("이벤트 등록 완료 - 제목: {}, 작성자: {}", eventDTO.getEventTitle(), userEmail);
        } else {
            eventService.eventUpdate(eventDTO);
            log.info("이벤트 수정 완료 - eventNum: {}", eventDTO.getEventNum());
        }

        return "redirect:/event/list";
    }

    // 이벤트 임시 저장 처리
    @PostMapping("/admin/tempSave")
    public String saveTempEvent(@ModelAttribute EventDTO eventDTO, RedirectAttributes redirectAttributes) throws Exception {
        log.info("이벤트 임시 저장 요청 - 제목: {}", eventDTO.getEventTitle());

        String userEmail = userSessionService.userEmail();
        String userRole = userSessionService.userRole();
        Long userNum = userSessionService.userNum();

        if (!userEmail.equals("admin") && !userRole.equals("ROLE_ADMIN")) {
            redirectAttributes.addFlashAttribute("errorMessage", "이벤트 임시 저장 권한이 없습니다.");
            log.warn("이벤트 임시 저장 권한 없음 - userEmail: {}, userRole: {}", userEmail, userRole);
            return "redirect:/event/list";
        }

        eventDTO.setUserNum(Math.toIntExact(userNum));

        if (eventDTO.getEventNum() == null) {
            eventService.saveTempEvent(eventDTO);
            log.info("이벤트 임시 저장 완료 - 제목: {}", eventDTO.getEventTitle());
        } else {
            eventService.updateTempEvent(eventDTO);
            log.info("임시 저장 이벤트 수정 완료 - eventNum: {}", eventDTO.getEventNum());
        }

        return "redirect:/event/list";
    }

    // 이벤트 상세보기 페이지
    @GetMapping("/detail")
    public String viewEvent(@RequestParam("eventNum") Long eventNum, Model model) {
        log.info("이벤트 상세보기 요청 - eventNum: {}", eventNum);

        EventDTO eventInfo = eventService.getEventById(eventNum);
        if (eventInfo != null) {
            // 날짜 형식 설정
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
            eventInfo.setFormattedCreateTime(eventInfo.getEventCreateTime().format(formatter));

            model.addAttribute("event", eventInfo);
            log.info("이벤트 상세 정보 로드 완료 - 제목: {}", eventInfo.getEventTitle());
        } else {
            log.warn("이벤트 정보가 존재하지 않음 - eventNum: {}", eventNum);
            model.addAttribute("errorMessage", "이벤트 정보를 찾을 수 없습니다.");
        }

        return "notifications/eventView"; // 상세보기 페이지로 이동
    }

    // 이벤트 삭제 처리
    @PostMapping("/admin/delete")
    public String deleteEvent(@RequestParam("eventNum") Long eventNum, RedirectAttributes redirectAttributes) throws Exception {
        log.info("이벤트 삭제 요청 - eventNum: {}", eventNum);

        String userEmail = userSessionService.userEmail();
        String userRole = userSessionService.userRole();

        if (!userEmail.equals("admin") && !userRole.equals("ROLE_ADMIN")) {
            redirectAttributes.addFlashAttribute("errorMessage", "이벤트 삭제 권한이 없습니다.");
            log.warn("이벤트 삭제 권한 없음 - userEmail: {}, userRole: {}", userEmail, userRole);
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
}
