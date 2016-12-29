package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {
    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testSave() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Юзер ланч", 510);
        Meal created = service.save(newMeal, USER_ID);
        newMeal.setId(created.getId());
        //Collection<Meal> m = service.getAll(-1);
        MATCHER.assertCollectionEquals(Arrays.asList(newMeal), service.getAll(USER_ID));
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL2, ADMIN_MEAL1, newMeal), service.getAll(-1));
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL2, ADMIN_MEAL1), service.getAll(ADMIN_ID));
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(100002, ADMIN_ID);
        MATCHER.assertEquals(meal, ADMIN_MEAL1);
    }

    @Test
    public void delete() throws Exception {
        service.delete(100002, ADMIN_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(ADMIN_MEAL2), service.getAll(ADMIN_ID));
    }

    @Test
    public void getBetweenDates() throws Exception {
        // default Collection<Meal> getBetweenDates(LocalDate startDate, LocalDate endDate, int userId)
        LocalDate startDate = LocalDate.of(2015, Month.JUNE, 1);
        LocalDate endDate = LocalDate.of(2015, Month.JUNE, 1);
        Collection<Meal> meals = service.getBetweenDates(startDate, endDate, 100001);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL2, ADMIN_MEAL1), meals);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        //Collection<Meal> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);
        LocalDateTime startDateTime = LocalDateTime.of(2015, Month.JUNE, 1, 14, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2015, Month.JUNE, 1, 21, 0);
        Collection<Meal> meals = service.getBetweenDateTimes(startDateTime, endDateTime, 100001);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL2, ADMIN_MEAL1), meals);
    }

    @Test
    public void getAll() throws Exception {
        // Collection<Meal> getAll(int userId);
        Collection<Meal> meals = service.getAll(100001);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL2, ADMIN_MEAL1), meals);
    }

    @Test
    public void update() throws Exception {
        // Meal update(Meal meal, int userId) throws NotFoundException;
        Meal updated = new Meal(100002, ADMIN_MEAL1.getDateTime(), ADMIN_MEAL1.getDescription(), ADMIN_MEAL1.getCalories());
        updated.setDescription("UpdatedMeal");
        updated.setCalories(330);
        service.update(updated, 100001);
        MATCHER.assertEquals(updated, service.get(updated.getId(), ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(110000, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        Meal meal = service.get(100002, 1);
        MATCHER.assertEquals(meal, ADMIN_MEAL1);
    }
}
