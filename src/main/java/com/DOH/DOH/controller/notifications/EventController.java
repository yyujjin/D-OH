package com.DOH.DOH.controller.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;
import com.DOH.DOH.service.notifications.EventService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("event")
public class EventController {

    @Autowired
    EventService eventService;

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    // 이벤트 목록 조회
    @GetMapping("/list")
    public String eventList(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        int totalPages = eventService.getTotalPages();  // 전체 페이지 수 계산

        // 이벤트 목록을 모델에 추가
        List<EventDTO> eventList = eventService.getEventList(page);
        model.addAttribute("eventList", eventList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        return "notifications/eventList";  // 이벤트 목록 페이지로 이동
    }

    // 이벤트 상세 조회
    @GetMapping("/detail")
    public String showEventDetail(@RequestParam(name = "eventNum", defaultValue = "0") int eventNum, Model model) {
        if (eventNum <= 0) {
            // 파라미터가 누락되었거나 유효하지 않은 경우 처리
            return "redirect:/event/list";  // 목록 페이지로 리다이렉트 또는 오류 페이지 표시
        }
        EventDTO event = eventService.getEventDetail(eventNum);
        model.addAttribute("event", event);
        return "notifications/eventView";
    }

    // 이벤트 작성 페이지 이동
    @GetMapping("/admin/write")
    public String showEventWritePage(Model model) {
        model.addAttribute("currentDate", new java.util.Date());
        return "notifications/eventWrite";  // 이벤트 작성 페이지로 이동
    }

    // 이벤트 등록
    @PostMapping("/admin/create")
    public String createEvent(@ModelAttribute EventDTO eventDTO,
                              @RequestParam("file") MultipartFile file,
                              HttpSession session,
                              Model model) throws IOException {
        // 세션에서 로그인된 사용자의 userNum 가져오기
        Integer loggedInUserNum = (Integer) session.getAttribute("userNum");
        if (loggedInUserNum == null) {
            throw new RuntimeException("로그인된 사용자가 없습니다.");
        }

        // userNum이 3이 아니면 등록을 차단
        if (!loggedInUserNum.equals(3)) {
            model.addAttribute("errorMessage", "이벤트 등록은 허용되지 않습니다.");
            return "redirect:/event/list";  // 목록 페이지로 리다이렉트
        }

        // 파일 업로드 로직
        if (!file.isEmpty()) {
            String fileUrl = uploadFileToS3Bucket(file);
            eventDTO.setEventImageUrl(fileUrl);
            eventDTO.setEventImageName(file.getOriginalFilename());
        }

        eventDTO.setUserNum(loggedInUserNum);  // userNum을 DTO에 설정
        eventService.writeEvent(eventDTO);
        return "redirect:/event/list";
    }


    // 이벤트 임시 저장
    @PostMapping("/admin/save")
    public String saveEvent(@ModelAttribute EventDTO eventDTO, @RequestParam("file") MultipartFile file) throws IOException {
        // 파일 업로드 로직 추가
        if (!file.isEmpty()) {
            String fileUrl = uploadFileToS3Bucket(file);
            eventDTO.setEventImageUrl(fileUrl);  // S3에 저장된 이미지 URL을 DTO에 설정
            eventDTO.setEventImageName(file.getOriginalFilename()); // 원본 파일명을 DTO에 설정
        }

        eventDTO.setEventTempSave(true);
        eventService.writeEvent(eventDTO);  // 임시 저장 후 목록으로 리다이렉트
        return "redirect:/event/list";
    }

    // 이벤트 삭제
    @PostMapping("/admin/delete")
    public String deleteEvent(@RequestParam("eventNum") int eventNum) {
        eventService.deleteEvent(eventNum);
        return "redirect:/event/list";  // 삭제 후 목록으로 리다이렉트
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
