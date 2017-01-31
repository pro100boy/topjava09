package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;

public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(MealsUtil.getWithExceeded(MEALS, AuthorizedUser.getCaloriesPerDay()))));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {

        List<Meal> meals = new ArrayList<>(MEALS);
        meals.remove(MEAL1);

        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());

        MATCHER.assertCollectionEquals(meals, mealService.getAll(AuthorizedUser.id()));
    }

    @Test
    public void testUpdate() throws Exception {

        Meal updated = getUpdated();
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, mealService.get(MEAL1_ID, AuthorizedUser.id()));
    }

    @Test
    public void testCreate() throws Exception {

        Meal expected = getCreated();

        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());

        Meal returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        List<Meal> list = new ArrayList<>(MEALS);
        list.add(0, returned);
        MATCHER.assertCollectionEquals(list, mealService.getAll(AuthorizedUser.id()));
    }

    @Test
    public void testGetBetween() throws Exception {
        String startDateTime = "2015-05-30T08:00:00";//LocalDateTime.of(DateTimeUtil.MIN_DATE, LocalTime.MIN).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String endDateTime = "2015-05-30T20:00:00";//LocalDateTime.of(DateTimeUtil.MAX_DATE, LocalTime.MAX).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        List<Meal> filteredList = new ArrayList<>(mealService.getBetweenDateTimes(
                LocalDateTime.parse(startDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                LocalDateTime.parse(endDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                AuthorizedUser.id()
        ));

        TestUtil.print(mockMvc.perform(get(REST_URL + "filter")
        .param("startDateTime", startDateTime)
        .param("endDateTime", endDateTime))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(MealsUtil.getWithExceeded(filteredList, AuthorizedUser.getCaloriesPerDay()))));
    }

    @Test
    public void testFilterWithNotDifinedParameters() throws Exception {
        mockMvc.perform(get(REST_URL + "filter")
                .param("startDateTime", "")
                .param("endDateTime", ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(MealsUtil.getWithExceeded(MEALS, AuthorizedUser.getCaloriesPerDay())));
    }

    @Test
    public void testFilterWithNotParameters() throws Exception {
        mockMvc.perform(get(REST_URL + "filter"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
