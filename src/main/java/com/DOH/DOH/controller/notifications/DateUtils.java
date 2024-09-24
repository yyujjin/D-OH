package com.DOH.DOH.controller.notifications;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // 문자열을 LocalDate로 변환
    public static LocalDate parseStringToLocalDate(String dateStr) throws DateTimeParseException {
        return LocalDate.parse(dateStr, FORMATTER);
    }

    // LocalDate를 문자열로 변환 (null 체크 포함)
    public static String formatLocalDate(LocalDate date) {
        if (date == null) {
            return "날짜 없음"; // 기본값으로 적절한 메시지 반환
        }
        return date.format(FORMATTER);
    }
}
