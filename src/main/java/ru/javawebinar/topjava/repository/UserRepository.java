package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.User;

import java.util.Collection;
import java.util.List;

public interface UserRepository {
    User save(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    User getByEmail(String email);

    List<User> getAll();

    //методы загрузки пользователей вместе с едой с учетом, что может быть FetchType.LAZY
    //для jdbc, jpa, datajpa своя реализация
    Collection<User> getAllWithMeals();

    User getWithMeals(int id);

    User updateLazy(User user);
}
