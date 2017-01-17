package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Collection;

public interface MealRepository {
    // null if updated meal do not belong to userId
    Meal save(Meal meal, int userId);

    // false if meal do not belong to userId
    boolean delete(int id, int userId);

    // null if meal do not belong to userId
    Meal get(int id, int userId);

    // ORDERED dateTime
    Collection<Meal> getAll(int userId);

    // ORDERED dateTime
    Collection<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);

    // ORDERED DATE, TIME
    public Collection<Meal> getAllWithUser(int userId);

    public Meal getWithUser(int id, int userId);
}
