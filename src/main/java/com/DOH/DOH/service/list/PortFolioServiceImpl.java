package com.DOH.DOH.service.list;

import com.DOH.DOH.dto.list.PortFolioDTO;
import com.DOH.DOH.mapper.list.PortFolioMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class PortFolioServiceImpl {

    @Autowired
    PortFolioMapper portFolioMapper;

    ArrayList<PortFolioDTO> getPortFolioList(){
      return portFolioMapper.getPortFolioList();
    };
}
