package com.DOH.DOH.service.list;

import com.DOH.DOH.dto.list.ContestListDTO;

import java.util.ArrayList;

public interface MainService {
    ArrayList<ContestListDTO> getImageList();
    ArrayList<ContestListDTO> getLatestList();
    ArrayList<ContestListDTO> getPriceList();
    ArrayList<ContestListDTO> getHitList();
}
