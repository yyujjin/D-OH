package com.DOH.DOH.service.list;

import com.DOH.DOH.dto.list.PortFolioDTO;

import java.util.ArrayList;

public interface PortFolioService {
    ArrayList<PortFolioDTO> getPortFolioList();
    void hitUp(int id);//조회수 증가 메서드
}
