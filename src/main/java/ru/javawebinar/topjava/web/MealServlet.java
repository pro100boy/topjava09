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
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private MealDAO mealDAO;

    public MealServlet() {
        super();
        this.mealDAO = new MealDAOMemoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to meals");
        List<MealWithExceed> mealsWithExceeded = MealsUtil.getFilteredWithExceeded(mealDAO.getAllMeal(), LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("meals", mealsWithExceeded);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
