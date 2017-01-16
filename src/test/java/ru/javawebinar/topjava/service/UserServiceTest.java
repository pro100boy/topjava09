package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.*;

import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
/*
abstract чтобы гарантировать вызов тестов из наследников
*/
public abstract class UserServiceTest {

    @Autowired
    protected UserService service;

    @Before
    public void setUp() throws Exception {
        service.evictCache();
    }

    @Test
    public void testSave() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", 1555, false, Collections.singleton(Role.ROLE_USER));
        User created = service.save(newUser);
        newUser.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, newUser, USER), service.getAll());
    }

    @Test(expected = DataAccessException.class)
    public void testDuplicateMailSave() throws Exception {
        service.save(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(USER_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(ADMIN), service.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void testGet() throws Exception {
        User user = service.get(USER_ID);
        MATCHER.assertEquals(USER, user);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void testGetByEmail() throws Exception {
        User user = service.getByEmail("user@yandex.ru");
        MATCHER.assertEquals(USER, user);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<User> all = service.getAll();
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, USER), all);
    }

/*
    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        service.update(updated);
        MATCHER.assertEquals(updated, service.get(USER_ID));
    }
        */
    @Test
    /* метод переопределен для:
    UserServiceTest_*_Jpa
    UserServiceTest_*_Datajpa
      */
    public void testUpdate() throws Exception {
        TestUser updated = new TestUser(USER);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        service.update(updated.asUser());
        MATCHER.assertEquals(updated, service.get(USER_ID));
    }

    /*
    Optional
    Тестим загрузку юзера с едой
    (внесены также необходимые изменения в UserTestData)
     */
    @Test
    public void testGetAllWithMeals() throws Exception {
        MATCHER.assertCollectionEquals(USERS_DEEP, service.getAllWithMeals());
    }

    /*
    Optional
    Тестим редактирование с учетом вложенности списка еды в юзера:
    добавлено едактирование списка еды (заодно протестился service.getWithMeals)
    Кроме того, при редактировании путем подстановки самодельного объекта (new TestUser/User(USER))
    выбивает ошибку при @OneToMany(... orphanRemoval = true).
    JPA не видит задекларированных связей и выбрасывает исключение.
    Вариант: брать объет из БД (service.getWithMeals) или
    если всеже работать с самодельным объектом - но он должен быть обвязан необходимыми связями.
    (для JDBC все это, естественно, не актуально)
    */
    @Test
    public void testUpdateWithMeals() throws Exception {
        //Вариант 1: User updated = service.getWithMeals(USER_ID); //редактируемый объект берем из БД вместе со списком еды
        //Вариант 2: создаем свой объект который будем класть в базу поверх существующего
        User updated = new User(USER);
        //создать список еды мало - он должен быть сам валидным с точки зрения связей с User - потому USER_MEALS_DEEP
        List<Meal> meals = new ArrayList<>(MealTestData.USER_MEALS_DEEP);
        updated.setMeals(meals);
        //... Вариант 2
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        if ((updated.getMeals() != null) && (!updated.getMeals().isEmpty()))
            updated.getMeals().remove(0);   //добавил удаление из связанного списка
        service.update(updated);
        MATCHER.assertEquals(updated, service.getWithMeals(USER_ID)); //заодно тест service.getWithMeals
    }
}