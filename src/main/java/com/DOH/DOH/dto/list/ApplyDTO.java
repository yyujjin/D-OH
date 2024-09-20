package com.DOH.DOH.dto.list;

import lombok.Data;

import java.util.Date;

@Data
public class ApplyDTO {
    private int conNum;
    private int userNum;
    private String applyTitle;
    private String applyContent;
    private Date applyDate;
}