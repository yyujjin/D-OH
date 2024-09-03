package com.DOH.DOH.service;

import com.DOH.DOH.mapper.TestMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestService {

    @Autowired
    TestMapper testMapper;

    public String test(int id) {
       return testMapper.test(id);
    }
}
