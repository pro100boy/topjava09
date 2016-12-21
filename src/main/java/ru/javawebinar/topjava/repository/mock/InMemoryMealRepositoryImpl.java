package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal));
    }

    @Override
    public Meal save(Meal meal) {
        LOG.info("save " + meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userID) {
        LOG.info("delete " + id);
        if (repository.get(id).getUserID() != userID)
            throw new NotFoundException("Нельзя удалять чужую еду");

        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userID) {
        LOG.info("get " + id);

        if (repository.get(id).getUserID() != userID)
            throw new NotFoundException("Нельзя смотреть чужую еду");
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        LOG.info("getAll");
        return (repository.values().isEmpty() ? Collections.emptyList() : repository.values());
    }

    @Override
    public Collection<Meal> getAll(int userID) {
        LOG.info("getAll for userID " + userID);
        //если еда отсутствует или чужая, возвращать null/false
        return (repository.values().isEmpty() ?
                Collections.emptyList() :
                repository.values().stream().filter(meal -> meal.getUserID() == userID).collect(Collectors.toList()));
    }

    @Override
    public Collection<Meal> getBetween(LocalDate startDate, LocalDate endDate, int userID) {
        LOG.info("getAll for userID {} between dates {} and {}", userID, startDate, endDate);
        //если еда отсутствует или чужая, возвращать null/false
        return getAll(userID).stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        InMemoryMealRepositoryImpl inMemoryMealRepository = new InMemoryMealRepositoryImpl();
        inMemoryMealRepository.getAll(1).forEach(System.out::println);
    }
}

