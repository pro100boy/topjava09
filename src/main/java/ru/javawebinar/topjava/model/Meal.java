package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class Meal extends BaseEntity{
    //private Integer id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private int userID;

    public Meal(LocalDateTime dateTime, String description, int calories, int userID) {
        this(null, dateTime, description, calories, userID);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories, int userID) {
        super(id);
        //this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.userID = userID;
    }

    /*public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }*/

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    /*public boolean isNew() {
        return id == null;
    }*/

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", userID=" + userID +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
