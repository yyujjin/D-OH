package com.DOH.DOH.service.user;

import com.DOH.DOH.dto.user.PortFolioUploadDTO;
import com.DOH.DOH.mapper.user.PortFolioUploadMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PortFolioUploadServiceImpl implements PortFolioUploadService{
    @Autowired
    PortFolioUploadMapper portFolioUploadMapper;

    @Override
    public PortFolioUploadDTO findByUserEmail(String userEmail) {
        log.info("findByUserEmail:{}", userEmail);
        return portFolioUploadMapper.findByUserEmail(userEmail);
    }
    @Override
    public void insert(PortFolioUploadDTO portFolioUploadDTO) {
        log.info("insert:{}", portFolioUploadDTO);
        portFolioUploadMapper.insert(portFolioUploadDTO);
    }

}
