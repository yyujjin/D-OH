package com.DOH.DOH.service.contest;

import com.DOH.DOH.dto.contest.ContestUploadDTO;
import com.DOH.DOH.mapper.contest.ContestUploadMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class ContestUploadServiceImpl implements ContestUploadService {

    @Autowired
    private ContestUploadMapper contestUploadMapper;

    @Override
    public void saveContest(ContestUploadDTO contestUploadDTO) {
        log.info("Saving ContestUploadDTO: {}", contestUploadDTO); // 로그 추가
        contestUploadMapper.insertContest(contestUploadDTO);
    }

    @Override
    public void updateOrderNumber(ContestUploadDTO contestUploadDTO) {
        contestUploadMapper.updateOrderNumber(contestUploadDTO);
    }

    @Override
    public ContestUploadDTO findContestById(Long conNum) {
        return contestUploadMapper.selectContestById(conNum);
    }

    @Override
    public List<String> getContestTypes() {
        return contestUploadMapper.findconTypes();
    }

    @Override
    @Transactional
    public String generateOrderNumber() {
        // 1. 오늘 날짜 생성 (yyMMdd 형식)
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("yyMMdd"));

        // 2. 오늘의 콘테스트 카운트 조회
        Integer contestCount = contestUploadMapper.findContestCountByDate(formattedDate);

        // 3. 오늘 처음 주문이면 카운트를 1로 설정, 아니면 증가
        if (contestCount == null) {
            contestCount = 1;
        } else {
            contestCount++;
        }

        // 4. 주문 번호 생성 (예: 240913-000001 형식)
        String orderNumber = String.format("%s-%07d", formattedDate, contestCount);

        // 5. 생성된 주문 번호 반환
        return orderNumber;
    }

        //컨테스트 지원자 목록 가져오기
        public List<String>getContestApplicants(Long contestNum){
            return contestUploadMapper.getContestApplicants(contestNum);
        }
}
