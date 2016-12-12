package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.impl.MealDAOMemoryImpl;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private MealDAO mealDAO;
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/mealaction.jsp";
    private static String LIST_MEAL = "/meals.jsp";

    public MealServlet() {
        super();
        this.mealDAO = new MealDAOMemoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to meals");

        // actions processing
        String forward="";
        String action = req.getParameter("action");

        if (action.equalsIgnoreCase("delete")){
            int id = Integer.parseInt(req.getParameter("id"));
            LOG.debug("delete meals " + id);
            mealDAO.deleteMeal(id);
            forward = LIST_MEAL;
            List<MealWithExceed> mealsWithExceeded = MealsUtil.getFilteredWithExceeded(mealDAO.getAllMeal(), LocalTime.MIN, LocalTime.MAX, 2000);
            req.setAttribute("meals", mealsWithExceeded);
        } /*else if (action.equalsIgnoreCase("edit")){
            LOG.debug("edit meals");
            forward = INSERT_OR_EDIT;
            int userId = Integer.parseInt(req.getParameter("id"));
            User user = mealDAO.getUserById(userId);
            req.setAttribute("user", user);
        }*/ else if (action.equalsIgnoreCase("listMeal")){
            forward = LIST_MEAL;
            List<MealWithExceed> mealsWithExceeded = MealsUtil.getFilteredWithExceeded(mealDAO.getAllMeal(), LocalTime.MIN, LocalTime.MAX, 2000);
            req.setAttribute("meals", mealsWithExceeded);
        } else {
            forward = INSERT_OR_EDIT;
        }


        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
