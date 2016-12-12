package ru.javawebinar.topjava.dao.impl;

import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDAOMemoryImpl implements MealDAO {
    private AtomicInteger id = new AtomicInteger(0);
    private final List<Meal> meals;

    public MealDAOMemoryImpl() {
        this.meals = Collections.synchronizedList(new ArrayList<Meal>() {{
            add(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
            add(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
            add(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
            add(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
            add(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
            add(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
            add(new Meal(id.incrementAndGet(), LocalDateTime.of(2016, Month.APRIL, 20, 10, 0), "Завтрак", 400));
            add(new Meal(id.incrementAndGet(), LocalDateTime.of(2016, Month.APRIL, 20, 13, 0), "Обед", 500));
            add(new Meal(id.incrementAndGet(), LocalDateTime.of(2016, Month.APRIL, 20, 20, 0), "Ужин", 910));
        }});
    }

    @Override
    public List<Meal> getAllMeal() {
        return meals;
    }

    @Override
    public void deleteMeal(int id) {
        ListIterator<Meal> iterator = meals.listIterator();
        while (iterator.hasNext()) {
            Meal m = iterator.next();
            if (id == m.getId()) {
                iterator.remove();
                break;
            }
        }
    }

    public static void main(String[] args) {
        MealDAOMemoryImpl mealDAOMemory = new MealDAOMemoryImpl();
        List<Meal> meals = mealDAOMemory.getAllMeal();
        meals.forEach(System.out::println);
        System.out.println(" ");
        mealDAOMemory.deleteMeal(9);
        meals.forEach(System.out::println);

    }
}
