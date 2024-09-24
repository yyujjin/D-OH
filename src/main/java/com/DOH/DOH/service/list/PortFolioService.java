package com.DOH.DOH.service.list;

import com.DOH.DOH.dto.list.PagingDTO;
import com.DOH.DOH.dto.list.PortFolioDTO;

import java.util.ArrayList;

public interface PortFolioService {
    ArrayList<PortFolioDTO> getPortFolioList(PagingDTO dto);
    void hitUp(int id);//조회수 증가 메서드
    int getTotalCount(); // 전체 포토폴리오 수를 조회하는 메서드
}
