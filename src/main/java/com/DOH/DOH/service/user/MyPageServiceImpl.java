package com.DOH.DOH.service.user;

import com.DOH.DOH.dto.user.MyPageDTO;
import com.DOH.DOH.mapper.user.MyPageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MyPageServiceImpl implements MyPageService{
    @Autowired
    private MyPageMapper myPageMapper;

    @Override
    public MyPageDTO findByuserEmail(String userEmail) {
        return myPageMapper.findByuserEmail(userEmail);
    }

    @Override
    public MyPageDTO profilePhoto(String userEmail) {
        return myPageMapper.profilePhoto(userEmail);
    }

    @Override
    public void insertProfilePhoto(MyPageDTO myPageDTO) {
        log.info("마이페이지 232323 : {}", myPageDTO);
        myPageMapper.insertProfilePhoto(myPageDTO);
    }

    @Override
    public void insertUserEmail(String userEmail) {
        myPageMapper.insertUserEmail(userEmail);
    }
}
