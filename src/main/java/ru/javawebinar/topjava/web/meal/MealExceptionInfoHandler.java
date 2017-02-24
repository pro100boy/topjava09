package ru.javawebinar.topjava.web.meal;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import ru.javawebinar.topjava.web.LocalExceptionInfoHandler;

/**
 * User: gkislin
 * Date: 23.09.2014
 */
@ControllerAdvice(assignableTypes = AbstractMealController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MealExceptionInfoHandler extends LocalExceptionInfoHandler {

    public MealExceptionInfoHandler() {
        super("exception.meals.duplicate_datetime");
    }
}
