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

import java.time.LocalDate;
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

        // 이벤트 최대 12개까지만 유지, 초과된 이벤트는 삭제
        if (eventList.size() > 12) {
            List<EventDTO> excessEvents = eventList.subList(12, eventList.size());
            excessEvents.forEach(event -> {
                eventService.deleteEvent(event.getEventNum());
                log.info("초과된 이벤트 삭제 - eventNum: {}", event.getEventNum());
            });
            eventList = eventList.subList(0, 12); // 12개까지만 유지
        }

        model.addAttribute("eventList", eventList);
        log.info("이벤트 목록 조회 완료 - 총 이벤트 수: {}", eventList.size());

        // 관리자라면 임시 저장된 이벤트 조회
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

    @PostMapping("/admin/create")
    public String eventWrite(@ModelAttribute EventDTO eventDTO, Model model,
                             @RequestParam(value = "file", required = false) MultipartFile file,
                             @RequestParam(value = "existingImageName", required = false) String existingImageName,  // 기존 이미지 이름 받기
                             @RequestParam(value = "eventCreateTime", required = false) String eventCreateTimeStr,
                             @RequestParam(value = "eventStartDate", required = false) String eventStartDateStr,
                             @RequestParam(value = "eventEndDate", required = false) String eventEndDateStr,
                             RedirectAttributes redirectAttributes) throws Exception {

        log.info("이벤트 작성 요청 - 제목: {}", eventDTO.getEventTitle());

        // 관리자 권한 확인
        if (!isAdminUser()) {
            addMessage(redirectAttributes, "이벤트 작성 권한이 없습니다.", true);
            return "redirect:/event/list";
        }

        // 세션에서 userNum 설정
        eventDTO.setUserNum(Math.toIntExact(userSessionService.userNum()));

        // 날짜 필드 검증 - null 또는 빈 값 체크
        if (!eventDTO.isEventTempSave()) { // 임시 저장이 아닌 경우에만 빈 값 검증
            if ((eventCreateTimeStr == null || eventCreateTimeStr.trim().isEmpty()) ||
                    (eventStartDateStr == null || eventStartDateStr.trim().isEmpty()) ||
                    (eventEndDateStr == null || eventEndDateStr.trim().isEmpty())) {

                // 필수 날짜가 입력되지 않으면 에러 메시지와 함께 리다이렉트
                redirectAttributes.addFlashAttribute("errorMessage", "생성일, 시작일, 종료일을 모두 입력해 주세요.");
                return "redirect:/event/admin/write";
            }
        }

// 날짜 변환 로직
        setEventDates(eventDTO, eventCreateTimeStr, eventStartDateStr, eventEndDateStr);

// 이미지 처리 로직
        processEventImage(file, existingImageName, eventDTO);

// 이벤트 저장 또는 수정 처리
        saveOrUpdateEvent(eventDTO, model);

// 성공 메시지 추가 후 이벤트 목록 페이지로 리다이렉트
        addMessage(redirectAttributes, "이벤트가 성공적으로 등록되었습니다.", false);
        return "redirect:/event/list";
    }

    // 날짜 변환 로직
    private void setEventDates(EventDTO eventDTO, String eventCreateTimeStr, String eventStartDateStr, String eventEndDateStr) {
        // 생성일이 null이거나 빈 문자열일 경우 현재 날짜로 설정
        if (eventCreateTimeStr != null && !eventCreateTimeStr.trim().isEmpty()) {
            eventDTO.setEventCreateTime(DateUtils.parseStringToLocalDate(eventCreateTimeStr));
        } else {
            eventDTO.setEventCreateTime(LocalDate.now());  // 기본값: 현재 날짜
        }

        // 시작일이 null이거나 빈 문자열일 경우 현재 날짜로 설정
        if (eventStartDateStr != null && !eventStartDateStr.trim().isEmpty()) {
            eventDTO.setEventStartDate(DateUtils.parseStringToLocalDate(eventStartDateStr));
        } else {
            eventDTO.setEventStartDate(LocalDate.now());  // 기본값: 현재 날짜
        }

        // 종료일이 null이거나 빈 문자열일 경우 현재 날짜로부터 7일 후로 설정
        if (eventEndDateStr != null && !eventEndDateStr.trim().isEmpty()) {
            eventDTO.setEventEndDate(DateUtils.parseStringToLocalDate(eventEndDateStr));
        } else {
            eventDTO.setEventEndDate(LocalDate.now().plusDays(7));  // 기본값: 현재 날짜로부터 7일 후
        }
    }

    private void processEventImage(MultipartFile file, String existingImageName, EventDTO eventDTO) {
        if (file != null && !file.isEmpty()) {
            try {
                String uploadedFileName = s3FileUploadService.uploadFileToS3Bucket(file);
                eventDTO.setEventImageName(uploadedFileName); // 새로운 이미지 설정
                log.info("새로운 파일이 S3에 업로드되었습니다: {}", uploadedFileName);

                if (existingImageName != null && !existingImageName.isEmpty()) {
                    s3FileUploadService.deleteFile(existingImageName);
                    log.info("기존 이미지가 S3에서 삭제되었습니다: {}", existingImageName);
                }
            } catch (Exception e) {
                throw new RuntimeException("이미지 업로드 중 오류 발생", e);
            }
        } else {
            eventDTO.setEventImageName(existingImageName); // 파일이 없으면 기존 이미지 유지
        }
    }

    private void saveOrUpdateEvent(EventDTO eventDTO, Model model) {
        if (eventDTO.getEventNum() == null) {
            eventService.eventRegister(eventDTO, model); // 새 이벤트 등록
            log.info("이벤트 등록 완료 - 제목: {}", eventDTO.getEventTitle());
        } else {
            eventService.eventUpdate(eventDTO, model); // 기존 이벤트 수정
            log.info("이벤트 수정 완료 - eventNum: {}", eventDTO.getEventNum());
        }
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

    // 이벤트 임시 저장 처리 (임시 저장 또는 수정 처리)
    @PostMapping("/admin/tempSaveEvent")
    public String saveTempEvent(@ModelAttribute EventDTO eventDTO, RedirectAttributes redirectAttributes) throws Exception {
        if (!isAdminUser()) {
            redirectAttributes.addFlashAttribute("errorMessage", "이벤트 임시 저장 권한이 없습니다.");
            return "redirect:/event/list";
        }

        List<EventDTO> tempEventList = eventService.getTempEventList();
        if (tempEventList.size() >= 3) {
            redirectAttributes.addFlashAttribute("errorMessage", "임시 저장된 이벤트는 최대 3개까지만 가능합니다.");
            return "redirect:/event/list";
        }

        eventDTO.setUserNum(Math.toIntExact(userSessionService.userNum()));

        if (eventDTO.getEventNum() == null) {
            eventService.saveTempEvent(eventDTO); // 새로운 임시 저장 이벤트
            log.info("이벤트 임시 저장 완료 - 제목: {}", eventDTO.getEventTitle());
        } else {
            eventService.updateTempEvent(eventDTO); // 이미 임시 저장된 이벤트 수정
            log.info("임시 저장 이벤트 수정 완료 - eventNum: {}", eventDTO.getEventNum());
        }

        return "redirect:/event/list";
    }
}
