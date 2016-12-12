package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import java.util.List;

public interface MealDAO {
    List<Meal> getAllMeal();
    void deleteMeal(int id);
    Meal getMealById(int id);
}
