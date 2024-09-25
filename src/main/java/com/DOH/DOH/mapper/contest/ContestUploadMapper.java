package com.DOH.DOH.mapper.contest;

import com.DOH.DOH.dto.contest.ContestUploadDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ContestUploadMapper {
    // 콘테스트 정보를 DB에 삽입
    void insertContest(ContestUploadDTO contestUploadDTO);

    // 주문번호 업데이트
    void updateOrderNumber(ContestUploadDTO contestUploadDTO);

    // ID로 콘테스트 조회
    ContestUploadDTO selectContestById(@Param("conNum") Long conNum);

    // 업종 목록 조회
    @Select("SELECT DISTINCT conType FROM contest_contype")
    List<String> findconTypes();  // 업종 목록을 가져오는 메서드

    // 오늘 날짜로 콘테스트 카운트 조회
    Integer findContestCountByDate(@Param("formattedDate") String formattedDate);

    //컨테스트 지원자 목록 가져오기
    List<String>getContestApplicants(Long contestNum);

    //유저 이메일로 생성한 컨테스트 목록 조회하기
    List<ContestUploadDTO>getContestsByUserEmail(String userEmail);

    //컨테스트 모집 마감으로 설정
    void finishContest(Long conNum);
}

