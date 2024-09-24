package com.DOH.DOH.controller.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;
import com.DOH.DOH.service.common.S3FileUploadService;
import com.DOH.DOH.service.notifications.EventService;
import com.DOH.DOH.service.user.UserSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("event")
public class EventController {

    private final EventService eventService;
    private final UserSessionService userSessionService;
    private final S3FileUploadService s3FileUploadService;

    @Autowired
    public EventController(EventService eventService, UserSessionService userSessionService, S3FileUploadService s3FileUploadService) {
        this.eventService = eventService;
        this.userSessionService = userSessionService;
        this.s3FileUploadService = s3FileUploadService;
    }

    // 이벤트 목록 조회
    @GetMapping("/list")
    public String eventList(Model model) {
        log.info("이벤트 목록 조회 요청");

        List<EventDTO> eventList = eventService.getEventListLimited();

        // 날짜 변환
        eventList.forEach(event -> {
            event.setFormattedCreateTime(DateUtils.formatLocalDate(event.getEventCreateTime()));
            event.setFormattedStartDate(DateUtils.formatLocalDate(event.getEventStartDate()));
            event.setFormattedEndDate(DateUtils.formatLocalDate(event.getEventEndDate()));
        });

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
    public String eventWriteForm(@RequestParam(value = "eventNum", required = false) Long eventNum, Model model, RedirectAttributes redirectAttributes) {
        log.info("이벤트 작성 페이지 요청 - eventNum: {}", eventNum);

        if (!isAdminUser()) {
            addMessage(redirectAttributes, "이벤트 작성 권한이 없습니다.", true);
            return "redirect:/event/list";
        }

        EventDTO eventDTO;
        if (eventNum != null) {
            eventDTO = eventService.getEventById(eventNum, model);
            model.addAttribute("eventDTO", eventDTO);
            log.info("이벤트 수정 페이지 로드 - eventNum: {}", eventNum);
        } else {
            eventDTO = new EventDTO();
            model.addAttribute("eventDTO", eventDTO);
            log.info("이벤트 신규 등록 페이지 로드");
        }

        // 기존 이미지 정보를 모델에 추가 (이벤트 수정 시)
        if (eventNum != null && eventDTO.getEventImageName() != null) {
            model.addAttribute("existingImage", eventDTO.getEventImageName());
        }

        return "notifications/eventWrite";
    }

    // 이벤트 등록(작성&수정)
    @PostMapping("/admin/create")
    public String eventWrite(@ModelAttribute EventDTO eventDTO, Model model,
                             @RequestParam(value = "file", required = false) MultipartFile file,
                             @RequestParam("existingImageName") String existingImageName,  // 기존 이미지 이름 받기
                             @RequestParam("eventCreateTime") String eventCreateTimeStr,
                             @RequestParam("eventStartDate") String eventStartDateStr,
                             @RequestParam("eventEndDate") String eventEndDateStr,
                             RedirectAttributes redirectAttributes) throws Exception {
        log.info("이벤트 작성 요청 - 제목: {}", eventDTO.getEventTitle());

        // 관리자 권한 확인
        if (!isAdminUser()) {
            addMessage(redirectAttributes, "이벤트 작성 권한이 없습니다.", true);
            return "redirect:/event/list";
        }

        // 세션에서 userNum 설정
        eventDTO.setUserNum(Math.toIntExact(userSessionService.userNum()));

        // 날짜 변환 로직
        try {
            eventDTO.setEventCreateTime(DateUtils.parseStringToLocalDate(eventCreateTimeStr));
            eventDTO.setEventStartDate(DateUtils.parseStringToLocalDate(eventStartDateStr));
            eventDTO.setEventEndDate(DateUtils.parseStringToLocalDate(eventEndDateStr));
        } catch (Exception e) {
            log.error("날짜 변환 오류: {}", e.getMessage());
            addMessage(redirectAttributes, "날짜 형식이 올바르지 않습니다.", true);
            return "redirect:/event/list";
        }

        // 이미지 처리 로직
        if (file != null && !file.isEmpty()) {
            // 새로운 파일 업로드
            try {
                String uploadedFileName = s3FileUploadService.uploadFileToS3Bucket(file);
                eventDTO.setEventImageName(uploadedFileName); // 새로운 이미지 이름 설정
                log.info("새로운 파일이 S3에 업로드되었습니다: {}", uploadedFileName);

                // 기존 이미지 삭제
                if (existingImageName != null && !existingImageName.isEmpty()) {
                    try {
                        s3FileUploadService.deleteFile(existingImageName);
                        log.info("기존 이미지가 S3에서 삭제되었습니다: {}", existingImageName);
                    } catch (Exception e) {
                        log.error("S3에서 기존 이미지를 삭제하는 데 실패했습니다: {}", e.getMessage());
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("새로운 이미지를 업로드하는 데 실패했습니다.", e);
            }
        } else {
            // 파일이 비어 있는 경우 기존 이미지를 유지
            eventDTO.setEventImageName(existingImageName);
            log.info("이미지 수정이 없으므로 기존 이미지를 유지합니다: {}", existingImageName);
        }

        // 이벤트 저장 또는 수정 처리
        if (eventDTO.getEventNum() == null) {
            // 새 이벤트 등록
            eventService.eventRegister(eventDTO, model);
            log.info("이벤트 등록 완료 - 제목: {}", eventDTO.getEventTitle());
        } else {
            // 기존 이벤트 수정
            eventService.eventUpdate(eventDTO, model);
            log.info("이벤트 수정 완료 - eventNum: {}", eventDTO.getEventNum());
        }

        addMessage(redirectAttributes, "이벤트가 성공적으로 등록되었습니다.", false);
        return "redirect:/event/list";
    }


    // 이벤트 삭제 처리
    @PostMapping("/admin/delete")
    public String deleteEvent(@RequestParam("eventNum") Long eventNum, RedirectAttributes redirectAttributes) {
        log.info("이벤트 삭제 요청 - eventNum: {}", eventNum);

        if (!isAdminUser()) {
            addMessage(redirectAttributes, "이벤트 삭제 권한이 없습니다.", true);
            return "redirect:/event/list";
        }

        eventService.deleteEvent(eventNum);
        log.info("이벤트 삭제 완료 - eventNum: {}", eventNum);

        addMessage(redirectAttributes, "이벤트가 성공적으로 삭제되었습니다.", false);
        return "redirect:/event/list";
    }

    // 관리자 권한 확인 메서드
    private boolean isAdminUser() {
        String userEmail = userSessionService.userEmail();
        String userRole = userSessionService.userRole();
        return userEmail.equals("admin") || userRole.equals("ROLE_ADMIN");
    }

    // 성공 및 실패 메시지 처리 메서드
    private void addMessage(RedirectAttributes redirectAttributes, String message, boolean isError) {
        if (isError) {
            redirectAttributes.addFlashAttribute("errorMessage", message);
        } else {
            redirectAttributes.addFlashAttribute("successMessage", message);
        }
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
