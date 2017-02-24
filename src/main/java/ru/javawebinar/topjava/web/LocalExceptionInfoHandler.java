package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.javawebinar.topjava.util.exception.ErrorInfo;

import javax.servlet.http.HttpServletRequest;

import static ru.javawebinar.topjava.web.ExceptionInfoHandler.logAndGetErrorInfo;

/**
 * gkislin
 * 22.02.2017
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public abstract class LocalExceptionInfoHandler {

    @Autowired
    private MessageSource messageSource;

    private final String duplicateKey;

    public LocalExceptionInfoHandler(String duplicateKey) {
        this.duplicateKey = duplicateKey;
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        return logAndGetErrorInfo(req,
                new DataIntegrityViolationException(
                        messageSource.getMessage(duplicateKey, null, LocaleContextHolder.getLocale())
                ), false);
    }

}