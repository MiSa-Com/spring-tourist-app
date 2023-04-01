package com.ms.tourist_app.application.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConvertDateTime {
    private ConvertDateTime() {
    }

    public static String convertLocalDateToString(LocalDate localDate, String format) {
        if (localDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return localDate.format(formatter);
        }
        return null;
    }

    public static boolean isStartTimeSmallOrEqualEndTime(LocalDate startTime, LocalDate endTime) {
        return startTime.isBefore(endTime) || startTime.equals(endTime);
    }

    public static boolean isStartTimeSmallEndTime(LocalDate startTime, LocalDate endTime) {
        return startTime.isBefore(endTime);
    }

    public static LocalDate convertStringToLocalDate(String date, String format) {
        if (StringUtils.isNotEmpty(date)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return LocalDate.parse(date, formatter);
        }
        return null;
    }
}
