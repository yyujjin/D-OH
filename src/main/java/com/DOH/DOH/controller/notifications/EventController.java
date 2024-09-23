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
        // 최대 12개의 이벤트 가져오기
        List<EventDTO> eventList = eventService.getEventListLimited();
        model.addAttribute("eventList", eventList);

        // 임시 저장된 이벤트 가져오기 (관리자만 표시)
        String userRole = userSessionService.userRole();
        if (userRole.equals("ROLE_ADMIN")) {
            List<EventDTO> tempEventList = eventService.getTempEventList();
            model.addAttribute("tempEventList", tempEventList);
        }
        return "notifications/eventList";  // 이벤트 목록 페이지로 이동
    }

    // 이벤트 작성(작성&수정) 페이지 렌더링
    @PostMapping("/admin/write")
    public String eventWriteForm(@RequestParam(value = "eventNum", required = false) Long eventNum, ModelMap modelMap) throws Exception {
        if (eventNum != null) {
            EventDTO eventInfo = eventService.getEventById(eventNum);
            modelMap.addAttribute("eventDTO", eventInfo);
            log.info("이벤트 수정 페이지 - eventNum: {}", eventNum);
        } else {
            modelMap.addAttribute("eventDTO", new EventDTO());
            log.info("이벤트 신규 등록 페이지");
        }
        return "events/eventWrite";
    }

    // 이벤트 작성(작성&수정) 처리 (POST로 처리)
    @PostMapping("/admin/register")
    public String eventWrite(@ModelAttribute EventDTO eventDTO, RedirectAttributes redirectAttributes) throws Exception {
        String userEmail = userSessionService.userEmail();
        String userRole = userSessionService.userRole();
        Long userNum = userSessionService.userNum();  // Retrieve userNum from session

        // 권한 검사: 관리자만 작성 가능
        if (!userEmail.equals("admin") && !userRole.equals("ROLE_ADMIN")) {
            redirectAttributes.addFlashAttribute("errorMessage", "이벤트 작성 권한이 없습니다.");
            return "redirect:/event/list";
        }

        // Set the userNum in the EventDTO
        eventDTO.setUserNum(Math.toIntExact(userNum));

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

    // 이벤트 임시 저장 처리 (임시 저장 또는 수정 처리)
    @PostMapping("/admin/tempSave")
    public String saveTempEvent(@ModelAttribute EventDTO eventDTO, RedirectAttributes redirectAttributes) throws Exception {
        String userEmail = userSessionService.userEmail();
        String userRole = userSessionService.userRole();
        Long userNum = userSessionService.userNum();  // Retrieve userNum from session

        // 권한 검사: 관리자만 임시 저장 가능
        if (!userEmail.equals("admin") && !userRole.equals("ROLE_ADMIN")) {
            redirectAttributes.addFlashAttribute("errorMessage", "이벤트 임시 저장 권한이 없습니다.");
            return "redirect:/event/list";
        }

        eventDTO.setUserNum(Math.toIntExact(userNum));

        // 임시 저장된 이벤트 수정 로직 추가
        if (eventDTO.getEventNum() == null) {
            eventService.saveTempEvent(eventDTO);
            log.info("이벤트 임시 저장 완료 - 제목: {}", eventDTO.getEventTitle());
        } else {
            eventService.updateTempEvent(eventDTO); // 이미 임시 저장된 이벤트 수정
            log.info("임시 저장 이벤트 수정 완료 - eventNum: {}", eventDTO.getEventNum());
        }

        return "redirect:/event/list";
    }

    // 이벤트 삭제 처리
    @PostMapping("/admin/delete")
    public String deleteEvent(@RequestParam("eventNum") Long eventNum, RedirectAttributes redirectAttributes) throws Exception {
        String userEmail = userSessionService.userEmail();
        String userRole = userSessionService.userRole();

        // 권한 검사: 관리자만 삭제 가능
        if (!userEmail.equals("admin") && !userRole.equals("ROLE_ADMIN")) {
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

        // 메타데이터 생성 및 설정
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize()); // 파일의 크기 설정

        // InputStream으로 파일을 읽어들임
        try (InputStream inputStream = file.getInputStream()) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata);
            amazonS3.putObject(putObjectRequest);
        }
        String fileUrl = amazonS3.getUrl(bucketName, fileName).toString();
        log.info("fileUrl 로 파일이 업로드 되었습니다. " + fileUrl);
        return fileUrl; // S3 URL 반환
    }
}
