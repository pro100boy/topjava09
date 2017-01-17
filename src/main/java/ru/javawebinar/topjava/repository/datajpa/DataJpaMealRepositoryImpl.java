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
    public List<Meal> getAll(int userId) {
        /*
        вариант
        return proxy.findAllByUserIdOrderByDateTimeDesc(userId);

        будет работать и так (если на вход надо дать объект)
        return proxy.findAllByUserOrderByDateTimeDesc(user);
        при, соответсвенно:
        List<UserMeal> findAllByUserOrderByDateTimeDesc(User user);

        МИНУС: запрос будет строиться через left join user
        (все самогенерирующиеся запросы стремятся к завязыванию таблиц в запросах, не моможет и
        findAllByUser_IdOrderByDateTimeDesc(userId))
        */

        //более экономный вриант (без left join user)
        //(метод хотя и не родной, но запрос не самогенерирующийся, а указан через @Query, в котором нет связи таблиц)
        return crudRepository.getAll(userId);


        /*аналогично (без left join user), но излишне навороченный:
        return proxy.findAll(new Specification<UserMeal>() {
            @Override
            public Predicate toPredicate(Root<UserMeal> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("user"), userId);
            }
        }
        , new Sort(Sort.Direction.DESC, "dateTime"));
        */

    }

    @Override
    public Meal get(int id, int userId) {
        /*
        вариант
        return proxy.findOneByIdAndUserId(id, userId);
        МИНУС: запрос будет строиться через left join user
        */

        //более экономный вриант (без left join user)
        return crudRepository.get(id, userId);

        /*аналогично (без left join user), но излишне навороченный:
        return proxy.findOne(new Specification<UserMeal>() {
            @Override
            public Predicate toPredicate(Root<UserMeal> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("id"), id),
                        criteriaBuilder.equal(root.get("user"), userId)
                );
            }
        });
        */
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
