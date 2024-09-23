package com.DOH.DOH.service.user;

import com.DOH.DOH.dto.user.PortFolioUploadDTO;

public interface PortFolioUploadService {
    PortFolioUploadDTO findByUserEmail(String userEmail);
    void insert(PortFolioUploadDTO portFolioUploadDTO);
}
