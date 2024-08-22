package dev.app.cocospring.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final String DateTImeFormat = "yyyy-MM-dd HH:mm:ss";

    // 현재 시간을 "yyyy-MM-dd HH:mm:ss" 형식의 문자열로 반환
    public static String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateTImeFormat);
        return now.format(formatter);
    }

    // 문자열을 Date 객체로 변환
    public static Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DateTImeFormat);
        return formatter.parse(dateString);
    }

}
