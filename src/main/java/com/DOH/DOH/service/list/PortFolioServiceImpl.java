package com.DOH.DOH.service.list;

import com.DOH.DOH.dto.list.PagingDTO;
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

    public ArrayList<PortFolioDTO> getPortFolioList(PagingDTO dto){
        int offset = (dto.getCurrentPage() - 1) * dto.getPageSize();  // 페이징 offset 계산
        int pageSize = dto.getPageSize();

        return portFolioMapper.getPortFolioList(offset, pageSize);
    }

    public void hitUp(int id){//조회수 증가 메서드
        portFolioMapper.hitUp(id);
    }

    public int getTotalCount() {// 전체 포토폴리오 수를 조회하는 메서드

        return portFolioMapper.getTotalCount();
    }
}
