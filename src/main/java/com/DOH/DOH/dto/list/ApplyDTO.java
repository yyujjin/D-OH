package com.DOH.DOH.dto.list;

import lombok.Data;

import java.util.Date;

@Data
public class ApplyDTO {
    private Long conNum;
    private String userEmail;
    private String applyTitle;
    private String applyContent;
    private Date applyDate;
    private String imageUrl;
}