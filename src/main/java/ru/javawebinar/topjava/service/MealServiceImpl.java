package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id, int userID) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, userID), id);
    }

    @Override
    public Meal get(int id, int userID) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, userID), id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.getAll();
    }

    @Override
    public Collection<Meal> getAll(int userID, LocalDate startDate, LocalDate endDate) throws NotFoundException {
        // у еды есть УНИКАЛЬНОЕ id, по которому можно определить из базы, чья она;
        // если еда не принадлежит авторизированному пользователю или отсутствует, в MealServiceImpl бросать NotFoundException
        /*if (startDate == null && endDate == null)
            return repository.getAll(userID);
        else {
            if (startDate == null) startDate = LocalDate.MIN;
            if (endDate == null) endDate = LocalDate.MAX;
            return repository.getBetween(startDate, endDate, userID);
        }*/
        return checkNotFoundWithId(repository.getBetween(startDate, endDate, userID), userID);
    }
}
