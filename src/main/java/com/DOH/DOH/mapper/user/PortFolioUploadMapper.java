package com.DOH.DOH.mapper.user;

import com.DOH.DOH.dto.user.PortFolioUploadDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PortFolioUploadMapper {
    PortFolioUploadDTO findByUserEmail(String userEmail);
    void insert(PortFolioUploadDTO portFolioUploadDTO);
}
