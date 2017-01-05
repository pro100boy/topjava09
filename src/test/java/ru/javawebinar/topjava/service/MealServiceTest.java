package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.results.PrintableResult;
import org.junit.rules.*;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThat;
import static org.junit.experimental.results.PrintableResult.testResult;
import static org.junit.experimental.results.ResultMatchers.failureCountIs;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(MealServiceTest.class);

    @Autowired
    protected MealService service;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Rule
    public TestRule globalTimeout = new Timeout(10000, TimeUnit.MILLISECONDS);

    @Test
    public void withoutResourcesUsage() throws Exception {
        System.out.println("simply string out");
    }

    private static final int DELETE_ITERATION_COUNT = 5;

    private static MealService serv;

/*    @Rule
    public TestWatcher watcher = new TestWatcher() {

        private double testTime;

        @Override
        protected void starting(Description description) {
            testTime = System.nanoTime();
            //System.out.println("starting");
        }

        @Override
        protected void finished(Description description) {
            System.out.println(description.getMethodName() + "finished. Time: " + String.valueOf(System.nanoTime() - testTime) + " ns");
        }
    };*/

    private static Map<String, Long> testsCompleteMap = new HashMap<>();

    @Rule
    // http://stackoverflow.com/a/27868954/7203956
    public Stopwatch stopwatch = new Stopwatch() {
        private void logInfo(Description description, long nanos) {
            LOG.warn(String.format("+++ Test %s spent %d microseconds",
                    description.getMethodName(), TimeUnit.NANOSECONDS.toMicros(nanos)));
            testsCompleteMap.put(description.getMethodName(), TimeUnit.NANOSECONDS.toMicros(nanos));
        }

        @Override
        protected void finished(long nanos, Description description) {
            logInfo(description, nanos);
        }
    };

    @AfterClass
    public static void printTestsCompleteTime()
    {
        LOG.info(testsCompleteMap.entrySet().toString());
        //testsCompleteMap.entrySet().stream().forEach(System.out::println);
    }

    @Rule
    public ExternalResource resource = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            /*
            service не может быть static (из-за @Autowired), поэтому не можем его использовать в static class MultimpleDeletingContainer
            //
            проще так:
            @Autowired
            public void setService(MealService service) {
                this.service = service;
                MealServiceTest.serv = service;
            }
            но нам нужны @Rule
             */
            if (MealServiceTest.serv == null) MealServiceTest.serv = service;
        };

    };

    public static class MultimpleDeletingContainer {
        /*
        static - обязательно, иначе collector будет разбивать: вместо одного коллектора с N ошибками будем получать N коллекторов с одной ошибкой
         */
        @Rule
        public ErrorCollector collector = new ErrorCollector();

        @Test
        public void example() {
            for (int i = 0; i < DELETE_ITERATION_COUNT; i++) {
                try {
                    serv.delete(MealTestData.MEAL1_ID, i);
                } catch (NotFoundException e) {
                    collector.addError(new Exception(e.getMessage()+" for user_id = "+i));
                }
            }
        }
    }

    @Test
    public void testDeleteNotFoundMultiple() {
        /*
        смысл теста - получить определенное число сбоев. Если получим меньшее число, то будут выведены те операции, где они произошли
        Если получим необходимое число сбоев - ничего не увидим
         */
        PrintableResult testResult = testResult(MultimpleDeletingContainer.class);
        assertThat(testResult, failureCountIs(DELETE_ITERATION_COUNT));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(MealTestData.MEAL1_ID, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), service.getAll(USER_ID));
    }

    /*
    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        service.delete(MEAL1_ID, 1);
    }
     */

    @Test
    public void testDeleteNotFound() throws Exception {
        exception.expect(NotFoundException.class);
        service.delete(MEAL1_ID, 1);
    }

    @Test
    public void testSave() throws Exception {
        Meal created = getCreated();
        service.save(created, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), service.getAll(USER_ID));
    }

    @Test
    public void testGet() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        MATCHER.assertEquals(ADMIN_MEAL1, actual);
    }

    @Test
    public void testGetNotFound() throws Exception {
        exception.expect(NotFoundException.class);
        service.get(MEAL1_ID, ADMIN_ID);
    }
    /*
    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(MEAL1_ID, ADMIN_ID);
    }
     */

    @Test
    public void testUpdate() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        MATCHER.assertEquals(updated, service.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        exception.expect(NotFoundException.class);
        service.update(MEAL1, ADMIN_ID);
    }

    /*
    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        service.update(MEAL1, ADMIN_ID);
    }
     */

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(MEALS, service.getAll(USER_ID));
    }

    @Test
    public void testGetBetween() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL3, MEAL2, MEAL1),
                service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 30), USER_ID));
    }
}