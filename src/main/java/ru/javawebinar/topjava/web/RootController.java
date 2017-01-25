package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.meal.MealController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {
    private static final Logger LOG = LoggerFactory.getLogger(MealController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private MealController mealController;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "index";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        return "redirect:meals";
    }

    /*=======================for index ============================*/
    @RequestMapping(value = "/index")
    public void index() {
        LOG.info("back home");
        root();
    }

    /*=======================for meals ============================*/

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String meals(Model model, HttpServletRequest request) {
        LOG.info("getAll");
        model.addAttribute("meals", mealController.getAll());
        model.addAttribute("selectedLanguage", request.getParameter("language"));
        model.addAttribute("factLanguage", request.getParameter("language"));
        return "meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET, params = "action=delete")
    public String deleteMeal(@RequestParam("id") int id) {
        LOG.info("Delete meal {}", id);
        mealController.delete(id);
        return "redirect:meals";
    }

    @RequestMapping(value = "/meals", params = "action=update", method = RequestMethod.GET)
    public String updateMeal(@RequestParam(value = "id", required = false) int id, Model model) {
        final Meal meal = mealController.get(id);
        model.addAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value = "/meals", params = "action=create", method = RequestMethod.GET)
    public String createMeal(Model model) {
        final Meal meal = new Meal(LocalDateTime.now(), "", 1000);
        model.addAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String createOrUpdateMeal(@RequestParam(value = "id", required = false) String id,
                                     @RequestParam("dateTime") String dateTime,
                                     @RequestParam("description") String description,
                                     @RequestParam("calories") int calories
    ) {
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(dateTime),
                description,
                calories);

        if (meal.isNew()) {
            LOG.info("Create {}", meal);
            mealController.create(meal);
        } else {
            LOG.info("Update {}", meal);
            mealController.update(meal, Integer.valueOf(id));
        }
        return "redirect:meals";
    }

    @RequestMapping(value = "/meals", params = "action=filter", method = RequestMethod.POST)
    public String filterMeal(@RequestParam("startDate") String startDate,
                             @RequestParam("endDate") String endDate,
                             @RequestParam("startTime") String startTime,
                             @RequestParam("endTime") String endTime,
                             Model model
    ) {
        model.addAttribute("meals", mealController.getBetween(
                DateTimeUtil.parseLocalDate(startDate),
                DateTimeUtil.parseLocalTime(startTime),
                DateTimeUtil.parseLocalDate(endDate),
                DateTimeUtil.parseLocalTime(endTime)));
        return "meals";
    }

    private int getId(String id) {
        String paramId = Objects.requireNonNull(id);
        return Integer.valueOf(paramId);
    }
}
