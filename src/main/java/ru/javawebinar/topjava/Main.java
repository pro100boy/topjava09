package ru.javawebinar.topjava;

import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        // java 7 Automatic resource management
        GenericXmlApplicationContext springContext = new GenericXmlApplicationContext();
        springContext.getEnvironment().setActiveProfiles(Profiles.HSQLDB, Profiles.DATAJPA);
        springContext.load("spring/spring-app.xml", "spring/spring-db.xml");
        springContext.refresh();
        System.out.println("Bean definition names: " + Arrays.toString(springContext.getBeanDefinitionNames()));
            /*AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));
            System.out.println();

            MealRestController mealController = appCtx.getBean(MealRestController.class);
            List<MealWithExceed> filteredMealsWithExceeded =
                    mealController.getBetween(
                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
            filteredMealsWithExceeded.forEach(System.out::println);*/
    }
}
