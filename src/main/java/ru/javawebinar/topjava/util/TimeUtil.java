package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;

/**
 * GKislin
 * 07.01.2015.
 */
public final class TimeUtil {

    private TimeUtil(){}
    // _DateTimeFormatter можно сделать один заранее_
    // паттерн можно брать из пропертиз
    private static DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder().appendPattern("dd.MM.yyyy HH:mm").toFormatter();

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static String getLocalDateTimeStr(LocalDateTime dateTime)
    {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT).format(dateTime);
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime/*, String pattern*/) {
        if (localDateTime == null) localDateTime = LocalDateTime.now();
        //return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
        return localDateTime.format(dateTimeFormatter);
    }

    public static LocalDateTime getLocalDateTime(LocalDateTime localDateTime) {
        return (localDateTime == null) ? LocalDateTime.now().withSecond(0).withNano(0) : localDateTime;
    }
}
