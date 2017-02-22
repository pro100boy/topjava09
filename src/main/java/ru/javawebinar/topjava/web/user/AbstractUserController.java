package ru.javawebinar.topjava.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

/**
 * User: gkislin
 */
public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService service;

    /*@Autowired(required = false)
    private ReloadableResourceBundleMessageSource messageSource;*/

    @Autowired
    private MessageSource messageSource;

    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public User get(int id) {
        log.info("get " + id);
        return service.get(id);
    }

    public User create(User user) {
        checkNew(user);
        log.info("create " + user);
        User u = null;
        try {
            u = service.save(user);
        } catch (DataIntegrityViolationException e) {
            throwExceptionWithErrMsg();
        }

        return u;
    }

    public void delete(int id) {
        log.info("delete " + id);
        service.delete(id);
    }

    public void update(User user, int id) {
        checkIdConsistent(user, id);
        log.info("update " + user);
        //service.update(user);
        try {
            service.update(user);
        } catch (DataIntegrityViolationException e) {
            throwExceptionWithErrMsg();
        }
    }

    public void update(UserTo userTo) {
        log.info("update " + userTo);
        checkIdConsistent(userTo, AuthorizedUser.id());
        //service.update(userTo);
        try {
            service.update(userTo);
        } catch (DataIntegrityViolationException e) {
            throwExceptionWithErrMsg();
        }
    }

    public User getByMail(String email) {
        log.info("getByEmail " + email);
        return service.getByEmail(email);
    }

    public void enable(int id, boolean enabled) {
        log.info((enabled ? "enable " : "disable ") + id);
        service.enable(id, enabled);
    }

    private void throwExceptionWithErrMsg() {
        throw new DataIntegrityViolationException(messageSource.getMessage("exception.email", null, LocaleContextHolder.getLocale()));
    }
}
