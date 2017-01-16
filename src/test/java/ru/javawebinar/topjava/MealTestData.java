package ru.javawebinar.topjava;

import org.hibernate.LazyInitializationException;
import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>();

    public static final int MEAL1_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL_ID = START_SEQ + 8;

    public static final Meal MEAL1 = new Meal(MEAL1_ID, of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(MEAL1_ID + 1, of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL3 = new Meal(MEAL1_ID + 2, of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL4 = new Meal(MEAL1_ID + 3, of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500);
    public static final Meal MEAL5 = new Meal(MEAL1_ID + 4, of(2015, Month.MAY, 31, 13, 0), "Обед", 1000);
    public static final Meal MEAL6 = new Meal(MEAL1_ID + 5, of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
    public static final Meal ADMIN_MEAL1 = new Meal(ADMIN_MEAL_ID, of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final Meal ADMIN_MEAL2 = new Meal(ADMIN_MEAL_ID + 1, of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);

    public static final List<Meal> MEALS = Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);

    public static final List<Meal> USER_MEALS = Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    public static final List<Meal> ADMIN_MEALS = Arrays.asList(ADMIN_MEAL2, ADMIN_MEAL1);

    public static final Meal MEAL1_DEEP = addUser(MEAL1);
    public static final List<Meal> USER_MEALS_DEEP = USER_MEALS
            .stream()
            .map(MealTestData::addUser)
            .collect(Collectors.toList());

    public static Meal getCreated() {
        return new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "Созданный ужин", 300);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL1_ID, MEAL1.getDateTime(), "Обновленный завтрак", 200);
    }

    //=========
    public static Meal addUser(Meal meal) {
        Meal m = new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
        m.setUser(UserTestData.USER);
        return m;
    }

    public static class TestUserMeal extends Meal {

        public TestUserMeal(Meal meal) {
            super(meal);
            if (meal.getUser() != null)
                this.setUser(meal.getUser());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            TestUserMeal that = (TestUserMeal) o;
            if (this.getUser() == null) {
                //that.getUser() не должен быть равен нулю - он читается из БД.
                //если в качестве образца передаем ленивый объект (не инициирован user),
                //то делаем ленивую проверку
                return this.toString().equals(that.toString());
            } else {
                //если передали полный объект, то делаем полную проверку
                try {
                    return this.toString().equals(that.toString())
                            && new UserTestData.TestUser(this.getUser()).equals(new UserTestData.TestUser(that.getUser()));
                    //&& this.getUser().toString().equals(that.getUser().toString());
                } catch (LazyInitializationException e) {
                    //возможен вариант, когда с образцом придет прокси в поле user (смотри тест JpaUserMealRepositoryImpl.testSave)
                    return this.toString().equals(that.toString());
                }
            }
        }
    }
}
