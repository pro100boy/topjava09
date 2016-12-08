package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                // LocalDateTime.of(int year, Month month, int dayOfMonth, int hour, int minute)
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500), // exceed = false
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),   // exceed = false
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),    // exceed = false
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),// exceed = true
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),    // exceed = true
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)     // exceed = true
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    private static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        //  return filtered list with correctly exceeded field
        // check for null
        // но проверять на пустой список не надо. Все итак отработает
        mealList = Objects.requireNonNull(mealList, "mealList must not be null!");
        // считаем калории по дням
        Map<LocalDate, Integer> mapCaloriesPerDay = mealList.stream()
                .collect(Collectors.groupingBy(m -> m.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        // заполняем возвращаемый список
        return mealList.stream()
                .filter(s -> TimeUtil.isBetween(s.getDateTime().toLocalTime(), startTime, endTime))
                .map(p -> new UserMealWithExceed(p.getDateTime(), p.getDescription(), p.getCalories(), mapCaloriesPerDay.get(p.getDateTime().toLocalDate()) > caloriesPerDay ? true : false))
                .collect(Collectors.toList());
    }
}
