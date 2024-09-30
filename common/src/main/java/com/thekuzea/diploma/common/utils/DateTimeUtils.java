package com.thekuzea.diploma.common.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.thekuzea.diploma.common.constant.GlobalConstants.EMPTY_STRING;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtils {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static String formatTime(final LocalTime time) {
        return time != null ? time.format(TIME_FORMATTER) : EMPTY_STRING;
    }
}
