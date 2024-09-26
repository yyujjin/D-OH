package com.DOH.DOH.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
public class MyPageProfileDTO {
    private Long id;
    private String userEmail;
    private String userNickName;
    private String profileOneLiner;
    private String profileIntroduce;
    private int commissionStatus;
    private String mittingLocation;

    private String messageStart;
    private String messageEnd;

    private String myPageUrl;
    private String skillName;
}
