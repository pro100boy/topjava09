package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;

public interface MealService {
    Meal save(Meal meal);

    void delete(int id, int userID) throws NotFoundException;;

    // null if not found
    Meal get(int id, int userID) throws NotFoundException;;

    Collection<Meal> getAll();

    Collection<Meal> getAll(int userID, LocalDate startDate, LocalDate endDate) throws NotFoundException;
}
