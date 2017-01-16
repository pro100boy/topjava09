package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/*
Вариант Profiles.HSQLDB_MEMORY только для JPA, чтобы не плодить
*/
@ActiveProfiles({Profiles.HSQLDB_MEMORY, Profiles.JPA})
public class MealServiceHsqldbMemoryJpaTest extends MealServiceTest {
    @Override
    public void testDelete() throws Exception {
        super.testDelete();
    }

    @Override
    public void testDeleteNotFound() throws Exception {
        super.testDeleteNotFound();
    }

    @Override
    public void testSave() throws Exception {
        super.testSave();
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
    public void testUpdate() throws Exception {
        super.testUpdate();
    }

    @Override
    public void testUpdateNotFound() throws Exception {
        super.testUpdateNotFound();
    }

    @Override
    public void testGetAll() throws Exception {
        super.testGetAll();
    }

    @Override
    public void testGetBetween() throws Exception {
        super.testGetBetween();
    }

    @Override
    public void testGetAllWithUser() throws Exception {
        super.testGetAllWithUser();
    }

    @Override
    public void testGetWithUser() throws Exception {
        super.testGetWithUser();
    }

    @Override
    public void testGetWithUserNotFound() throws Exception {
        super.testGetWithUserNotFound();
    }
}
