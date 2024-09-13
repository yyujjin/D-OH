package com.DOH.DOH.service.contest;

import com.DOH.DOH.dto.contest.ContestUploadDTO;
import com.DOH.DOH.mapper.contest.ContestUploadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ContestUploadServiceImpl implements ContestUploadService {
    @Autowired
    private ContestUploadMapper contestMapper;

    @Override
    public void saveContest(ContestUploadDTO contestUploadDTO) {
        contestMapper.insertContest(contestUploadDTO);
    }

    @Override
    public ContestUploadDTO findContestById(Long contestId) {
        return contestMapper.selectContestById(contestId);
    }

    // 업종 목록을 가져오는 메서드 구현
    @Override
    public List<String> getContestTypes() {
        return contestMapper.findconTypes();
    }

    @Autowired
    private ContestUploadMapper contestUploadMapper;

    @Override
    @Transactional
    public String generateOrderNumber() {
        // 1. 오늘 날짜 생성 (yyMMdd 형식)
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("yyMMdd"));

        // 2. 오늘의 주문 카운트 조회 (orders 테이블에서)
        Integer orderCount = contestUploadMapper.findOrderCountByDate(formattedDate);

        // 3. 오늘 처음 주문이면 카운트를 1로 설정, 아니면 증가
        if (orderCount == null) {
            orderCount = 1;
        } else {
            orderCount++;
        }

        // 4. 주문 번호 생성 (예: 240913-000001 형식)
        String orderNumber = String.format("%s-%06d", formattedDate, orderCount);

        // 5. 주문 번호를 DB에 저장
        contestUploadMapper.insertOrder(orderNumber);

        // 6. 생성된 주문 번호 반환
        return orderNumber;
    }
}
