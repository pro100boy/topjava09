package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMealWithExceed {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    @Override
    public String toString() {
        return "\nUserMealWithExceed {" +
                "dateTime=" + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(dateTime) +
                ", description='" + description + "'" +
                ", calories=" + calories +
                ", exceed=" + exceed +
                "}";
    }
}
