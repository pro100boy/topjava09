package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Controller
public class MealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal save(Meal meal) {
        LOG.info("save " + meal);
        meal.setUserID(AuthorizedUser.id());
        return service.save(meal);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id, AuthorizedUser.id());
    }

    public Meal get(int id) {
        LOG.info("get " + id);
        return service.get(id, AuthorizedUser.id());
    }

    public Collection<Meal> getAll() {
        LOG.info("getAll");
        return service.getAll();
    }

    public Collection<Meal> getAll(LocalDate startDate, LocalDate endDate) throws NotFoundException {
        LOG.info("getAll for userID " + AuthorizedUser.id());
        return service.getAll(AuthorizedUser.id(), startDate, endDate);
    }

    // Отдать свою еду (для отображения в таблице, формат List<MealWithExceed>), запрос БЕЗ параметров
    public List<MealWithExceed> getMealWithExceed() {
        LOG.info("getMealWithExceed");
        return getMealWithExceed(LocalDate.MIN, LocalDate.MAX, LocalTime.MIN, LocalTime.MAX);
    }

    // Отдать свою еду, отфильтрованную по startDate, startTime, endDate, endTime
    public List<MealWithExceed> getMealWithExceed(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        LOG.info("getMealWithExceed between {} {} and {} {}", startDate.toString(), startTime.toString(), endDate.toString(), endTime.toString());
        return MealsUtil.getFilteredWithExceeded(
                getAll(startDate, endDate),
                startTime, endTime,
                MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
}
