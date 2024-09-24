package com.DOH.DOH.service.list;

import com.DOH.DOH.dto.list.PortFolioDTO;
import com.DOH.DOH.mapper.list.PortFolioMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class PortFolioServiceImpl implements PortFolioService {

    @Autowired
    PortFolioMapper portFolioMapper;

    public ArrayList<PortFolioDTO> getPortFolioList(){

        return portFolioMapper.getPortFolioList();
    }

    public void hitUp(int id){//조회수 증가 메서드
        portFolioMapper.hitUp(id);
    }
}
