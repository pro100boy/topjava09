package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles({Profiles.HSQLDB_MEMORY, Profiles.JPA})
public class UserServiceHsqldbMemoryJpaTest extends UserServiceTest {
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    public void testSave() throws Exception {
        super.testSave();
    }

    @Override
    public void testDuplicateMailSave() throws Exception {
        super.testDuplicateMailSave();
    }

    @Override
    public void testDelete() throws Exception {
        super.testDelete();
    }

    @Override
    public void testNotFoundDelete() throws Exception {
        super.testNotFoundDelete();
    }

    @Override
    public void testGet() throws Exception {
        super.testGet();
    }

    @Override
    public void testGetNotFound() throws Exception {
        super.testGetNotFound();
    }

    @Override
    public void testGetByEmail() throws Exception {
        super.testGetByEmail();
    }

    @Override
    public void testGetAll() throws Exception {
        super.testGetAll();
    }

    @Override
    public void testUpdate() throws Exception {
        UserTestData.TestUser updated = new UserTestData.TestUser(USER);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        service.updateLazy(updated.asUser());   //вместо service.update: при включенных cascade = CascadeType.ALL,
        //и orphanRemoval = true сохранение такого объекта не выйдет. А включено для варианта редактирования с учетом
        //связи с едой - см. testUpdateWithMeals() - для изучения такого способа работы
        MATCHER.assertEquals(updated, service.get(USER_ID));
    }

    @Override
    public void testGetAllWithMeals() throws Exception {
        super.testGetAllWithMeals();
    }
}
