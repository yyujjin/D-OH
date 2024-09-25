package com.DOH.DOH.service.list;

import com.DOH.DOH.dto.list.ContestListDTO;
import com.DOH.DOH.mapper.list.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MainServiceImpl implements MainService{

    @Autowired
    private MainMapper mainMapper;

    @Override
    public ArrayList<ContestListDTO> getImageList(){

        return mainMapper.getImageList();
    }

    @Override
    public ArrayList<ContestListDTO> getLatestList(){

        return mainMapper.getLatestList();
    }

    @Override
    public ArrayList<ContestListDTO> getPriceList(){
        return mainMapper.getPriceList();
    }

    @Override
    public ArrayList<ContestListDTO> getHitList(){
        return mainMapper.getHitList();
    }
}
