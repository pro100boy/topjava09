package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    @Autowired
    private CrudMealRepository crudRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            User user = crudUserRepository.findOne(userId);
            meal.setUser(user);
            return crudRepository.save(meal);
        } else {
            Meal m = crudRepository.get(meal.getId(), userId);
            if (m == null) return null;
            meal.setUser(m.getUser());
            return crudRepository.save(meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.get(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.getAll(userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudRepository.getBetween(startDate, endDate, userId);
    }

    @Override
    public Collection<Meal> getAllWithUser(int userId) {
        return crudRepository.getAllWithUser(userId);
    }

    @Override
    public Meal getWithUser(int id, int userId) {
        return crudRepository.getByIdWithUser(id, userId);
    }
}
