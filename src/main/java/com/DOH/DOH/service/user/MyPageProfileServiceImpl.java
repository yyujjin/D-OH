package com.DOH.DOH.service.user;

import com.DOH.DOH.dto.user.MyPageProfileDTO;
import com.DOH.DOH.dto.user.MyPageSkillDTO;
import com.DOH.DOH.mapper.user.MyPageProfileMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class MyPageProfileServiceImpl implements MyPageProfileService {

    @Autowired
    private MyPageProfileMapper myPageProfileMapper;

    @Override
    public void insert(MyPageProfileDTO myPageProfileDTO) {
        log.info("myPageProfileDTO:{}", myPageProfileDTO);
        myPageProfileMapper.insert(myPageProfileDTO);
    }

    @Override
    public MyPageProfileDTO findByUserEmail(String userEmail) {
        log.info("findByUserEmail:{}", userEmail);
        return myPageProfileMapper.findByUserEmail(userEmail);
    }

    @Override
    public MyPageProfileDTO findIdByUserEmail(@Param("id") Long id, String userEmail) {
        log.info("id:{} findIdByUserEmail:{}", id, userEmail);
        return myPageProfileMapper.findIdByUserEmail(id, userEmail);
    }

    @Override
    public void update(MyPageProfileDTO myPageProfileDTO) {
        log.info("myPageProfileDTOUPDATE:{}", myPageProfileDTO);
        myPageProfileMapper.update(myPageProfileDTO);
    }
}
