package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final Meal ADMIN_MEAL1 = new Meal(100002, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final Meal ADMIN_MEAL2 = new Meal(100003, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>();/*
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getDateTime(), actual.getDateTime())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getCalories(), actual.getCalories())
                            && Objects.equals(expected.getDescription(), actual.getDescription())
//                            && Objects.equals(expected.getRoles(), actual.getRoles())
                    )
    );*/

}
