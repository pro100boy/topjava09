package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LocalDateTimeConverter implements Converter<String, LocalDateTime> {
    private final DateTimeFormatter formatter;

    public LocalDateTimeConverter(String dateFormat) {
        this.formatter = DateTimeFormatter.ofPattern(dateFormat);
    }

    @Override
    public LocalDateTime convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }

        return LocalDateTime.parse(source, formatter);
    }
}

class A
{
    public static void main(String[] args) {
        LocalDateTimeConverter localDateTimeConverter = new LocalDateTimeConverter("yyyy-MM-dd HH:mm");
        System.out.println(localDateTimeConverter.convert("2015-05-30 08:00"));
        localDateTimeConverter = new LocalDateTimeConverter("yyyy-MM-dd HH:mm:ss");
        System.out.println(localDateTimeConverter.convert("2015-05-30 08:00:00"));
    }
}
