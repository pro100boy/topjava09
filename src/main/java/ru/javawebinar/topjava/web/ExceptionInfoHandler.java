package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: gkislin
 * Date: 23.09.2014
 */
@ControllerAdvice(annotations = RestController.class)
public class ExceptionInfoHandler {
    Logger LOG = LoggerFactory.getLogger(ExceptionInfoHandler.class);

//  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)    // 422
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        return logAndGetErrorInfo(req, e, true);
    }

    // BAD_REQUEST(400, "Bad Request"),
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(BindException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE + 2)
    public ErrorInfo badRequest(HttpServletRequest req, BindException e) {
        return logAndGetErrorInfo(req, e);
    }

    // for REST
    // http://stackoverflow.com/a/9003679
    // https://www.petrikainulainen.net/programming/spring-framework/spring-from-the-trenches-adding-validation-to-a-rest-api/
    // https://dzone.com/articles/spring-31-valid-requestbody
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE + 2)
    public ErrorInfo restValidationError(HttpServletRequest req, MethodArgumentNotValidException e) {
        return logAndGetErrorInfo(req, e.getBindingResult());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)   // 500
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @Order(Ordered.LOWEST_PRECEDENCE)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true);
    }

    public ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException) {
        if (logException) {
            LOG.error("Exception at request " + req.getRequestURL(), e);
        } else {
            LOG.warn("Exception at request " + req.getRequestURL() + ": " + e.toString());
        }
        return new ErrorInfo(req.getRequestURL(), e);
    }

    public ErrorInfo logAndGetErrorInfo(HttpServletRequest req, BindingResult bindingResult) {

        /*List<String> errorList = new ArrayList<>();

        for(FieldError fe : bindingResult.getFieldErrors())
            errorList.add(fe.getField() + " : " + fe.getDefaultMessage());*/

        List<String> errorList = bindingResult.getFieldErrors().stream().map(fe -> fe.getField() + " : " + fe.getDefaultMessage()).collect(Collectors.toList());

        LOG.warn("Validation exception at request " + req.getRequestURL() + ": " + errorList.toString());
        return new ErrorInfo(req.getRequestURL(), "ValidationException", errorList.toString());
    }
}
