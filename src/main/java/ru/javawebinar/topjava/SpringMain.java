package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: ");
            Arrays.asList(appCtx.getBeanDefinitionNames()).forEach(System.out::println);
            /*AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));
            //adminUserController.getAll().forEach(System.out::println);

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.getAll(1).forEach(System.out::println);*/
        }
    }
}
