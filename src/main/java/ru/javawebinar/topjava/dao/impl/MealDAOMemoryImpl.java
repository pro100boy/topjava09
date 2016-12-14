package ru.javawebinar.topjava.dao.impl;

import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDAOMemoryImpl implements MealDAO {
    private static final AtomicInteger id = new AtomicInteger(0);
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
        for (ListIterator<Meal> iterator = meals.listIterator(); iterator.hasNext(); ) {
            Meal m = iterator.next();
            if (id == m.getId()) {
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public Meal getMealById(int id) {
        Meal meal = null;
        for (ListIterator<Meal> iterator = meals.listIterator(); iterator.hasNext(); ) {
            Meal m = iterator.next();
            if (id == m.getId()) {
                meal = m;
                break;
            }
        }
        return meal;
    }

    @Override
    public void addMeal(Meal meal) {
        // определяем max id
        /*id.set(meals
                .stream()
                .mapToInt(Meal::getId)
                .max()
                .getAsInt());*/
        meal.setId(id.incrementAndGet());
        meals.add(meal);
    }

    @Override
    public void updateMeal(Meal meal) {
        for (ListIterator<Meal> iterator = meals.listIterator(); iterator.hasNext(); ) {
            Meal m = iterator.next();
            if (meal.getId() == m.getId()) {
                iterator.set(meal);
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
        mealDAOMemory.deleteMeal(7);
        mealDAOMemory.deleteMeal(1);
        meals.forEach(System.out::println);

        mealDAOMemory.addMeal(new Meal(0, LocalDateTime.of(2017, Month.APRIL, 20, 20, 0), "Ужин", 910));
        System.out.println(" ");
        meals.forEach(System.out::println);

        Meal m = new Meal();
        m.setId(2);
        m.setDescription("Новая еда");
        m.setCalories(6000);
        m.setDateTime(LocalDateTime.now());
        mealDAOMemory.updateMeal(m);
        System.out.println(" ");
        meals.forEach(System.out::println);
    }
}
