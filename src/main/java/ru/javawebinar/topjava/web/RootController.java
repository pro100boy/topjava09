package ru.javawebinar.topjava.web;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;
import ru.javawebinar.topjava.web.user.AbstractUserController;

import javax.validation.Valid;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController extends AbstractUserController {

    @GetMapping("/")
    public String root() {
        return "redirect:meals";
    }

    //    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String users() {
        return "users";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping("/meals")
    public String meals() {
        return "meals";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status) {
        // плохой код
        /*if (result.hasErrors()) {
            return "profile";
        } else {
            try {
                super.update(userTo);
                AuthorizedUser.get().update(userTo);
                status.setComplete();
                return "redirect:meals";
            } catch (DataIntegrityViolationException e) {
                result.rejectValue("email", "exception.email");
            }
        }
        return "profile";*/

        // наверное нужно так
        if (!result.hasErrors())
        {
            try {
                super.update(userTo);
                AuthorizedUser.get().update(userTo);
                status.setComplete();
                return "redirect:meals";
            } catch (DataIntegrityViolationException e) {
                result.rejectValue("email", "exception.email");
            }
        }
        return "profile";
    }

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        /*if (result.hasErrors()) {
            model.addAttribute("register", true);
            return "profile";
        } else {
            try {
                super.create(UserUtil.createNewFromTo(userTo));
                status.setComplete();
                return "redirect:login?message=app.registered&username=" + userTo.getEmail();
            } catch (DataIntegrityViolationException e) {
                result.rejectValue("email", "exception.email");
            }
        }
        return "profile";*/

        if (!result.hasErrors()) {
            try {
                super.create(UserUtil.createNewFromTo(userTo));
                status.setComplete();
                return "redirect:login?message=app.registered&username=" + userTo.getEmail();
            } catch (DataIntegrityViolationException ex) {
                result.rejectValue("email", "exception.email");
            }
        }
        // если ошибок в result нет, то сюда выполнение не дойдет
        model.addAttribute("register", true);
        return "profile";
    }
}
