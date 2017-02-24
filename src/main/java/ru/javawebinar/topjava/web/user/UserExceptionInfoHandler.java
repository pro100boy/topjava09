package ru.javawebinar.topjava.web.user;

import org.springframework.web.bind.annotation.ControllerAdvice;
import ru.javawebinar.topjava.web.LocalExceptionInfoHandler;

/**
 * User: gkislin
 * Date: 23.09.2014
 */
@ControllerAdvice(assignableTypes = AbstractUserController.class)
public class UserExceptionInfoHandler extends LocalExceptionInfoHandler {

    public UserExceptionInfoHandler() {
        super("exception.users.duplicate_email");
    }
}
