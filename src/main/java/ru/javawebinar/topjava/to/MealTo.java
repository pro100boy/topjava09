package ru.javawebinar.topjava.to;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

public class MealTo implements Serializable {
    private static final long serialVersionUID = 2L;

    protected Integer id;

    @NotNull(message = " must not be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    protected LocalDateTime dateTime;

    @NotBlank
    protected String description;

    @Range(min = 10, max = 5000)
    @NotNull(message = " must not be empty")
    protected Integer calories;

    public MealTo() {
    }

    public MealTo(int id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return "UserMealTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
