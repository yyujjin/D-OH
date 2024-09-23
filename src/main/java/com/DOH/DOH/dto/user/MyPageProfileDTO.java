package com.DOH.DOH.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPageProfileDTO {
    int id;
    String userEmail;
    String userNickName;
    String profileOneLiner;
    String profileIntroduce;
    int commissionStatus;
    String mittingLocation;

    @JsonFormat(pattern = "HH:mm")
    String messageStart;

    @JsonFormat(pattern = "HH:mm")
    String messageEnd;

    String myPageUrl;
}
