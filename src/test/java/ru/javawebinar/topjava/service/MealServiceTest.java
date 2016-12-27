package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static ru.javawebinar.topjava.MealTestData.*;

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
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL1, ADMIN_MEAL2, newMeal), service.getAll(-1));
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL1, ADMIN_MEAL2), service.getAll(ADMIN_ID));
    }
}
