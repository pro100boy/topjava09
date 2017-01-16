package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    @Query(name = Meal.DELETE)
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    @Transactional
    Meal save(Meal userMeal);

    @Query(name = Meal.GET)
    Meal get(@Param("id")Integer id, @Param("userId")Integer userId);
    //альтернатива UserMeal findOne(Specification<UserMeal> spec);

    @Query(name = Meal.ALL_SORTED)
    List<Meal> getAll(@Param("userId")Integer userId);
    /*
    Вариант: без @Query
    //реализация в интерфейсе, чтобы оставаться в контексте текущей Session, что необходимо для
    //возможности дергать ленивый прокси-объект
    default public Collection<UserMeal> getAllWithUser(int userId) {
        List<UserMeal> meals = findAll(userId);
        if (!meals.isEmpty()) meals.iterator().next().getUser().getId(); //весь список не нужен - юзер один на всех
        return meals;
    }*/

    @Query(name = Meal.GET_BETWEEN)
    List<Meal> getBetween(@Param("startDate") LocalDateTime startDate,
                                  @Param("endDate") LocalDateTime endDate,
                                  @Param("userId")Integer userId);

    @Query("SELECT m FROM Meal m JOIN FETCH m.user u WHERE u.id = :user_id ORDER BY m.dateTime DESC")
    public List<Meal> getAllWithUser(@Param("user_id")int userId);

    @Query("SELECT m FROM Meal m JOIN FETCH m.user u WHERE u.id = :user_id AND m.id=:id ORDER BY m.dateTime DESC")
    public Meal getByIdWithUser(@Param("id")int id, @Param("user_id")int userId);
}
