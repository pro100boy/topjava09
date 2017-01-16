package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 24.09.2015.
 */
public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);

    public static final ModelMatcher<User> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getPassword(), actual.getPassword())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getEmail(), actual.getEmail())
                            && Objects.equals(expected.getCaloriesPerDay(), actual.getCaloriesPerDay())
                            && Objects.equals(expected.isEnabled(), actual.isEnabled())
//                            && Objects.equals(expected.getRoles(), actual.getRoles())
                    )
    );

    public static final List<User> USERS = Arrays.asList(ADMIN, USER);

    public static final List<User> USERS_DEEP = USERS
            .stream()
            .map(UserTestData::addMeals)
            .collect(Collectors.toList());

    public static User addMeals(User user) {
        User u = new User(user);
        if (user.equals(USER)) u.setMeals(MealTestData.USER_MEALS);
        if (user.equals(ADMIN)) u.setMeals(MealTestData.ADMIN_MEALS);
        return u;
    }

    public static class TestUser extends User {

        public TestUser(User u) {
            this(u.getId(), u.getName(), u.getEmail(), u.getPassword(), u.getCaloriesPerDay(), u.isEnabled(), u.getRoles());
            if (u.getMeals() != null)
                this.setMeals(u.getMeals());
        }

        public TestUser(String name, String email, String password, Role role, Role... roles) {
            this(null, name, email, password, MealsUtil.DEFAULT_CALORIES_PER_DAY, true, EnumSet.of(role, roles));
        }

        public TestUser(Integer id, String name, String email, String password, int caloriesPerDay, boolean enabled, Set<Role> roles) {
            super(id, name, email, password, caloriesPerDay, enabled, roles);
        }

        public User asUser() {
            return new User(this);
        }

        @Override
        public String toString() {
            return "User (" +
                    "id=" + id +
                    ", email=" + email +
                    ", name=" + name +
                    ", enabled=" + enabled +
                    ", password=" + password +
                    ", authorities=" + roles +
                    ')';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            TestUser that = (TestUser) o;
            if (this.getMeals() == null) {
                //если в качестве образца передаем ленивый объект (не инициирован список еды)
                //то делаем ленивую проверку
                return Objects.equals(this.password, that.password)
                        && Objects.equals(this.id, that.id)
                        && Objects.equals(this.name, that.name)
                        && Objects.equals(this.email, that.email)
                        && Objects.equals(this.caloriesPerDay, that.caloriesPerDay)
                        && Objects.equals(this.enabled, that.enabled);
            } else {
                //если передали полный объект, то делаем полную проверку
                return Objects.equals(this.password, that.password)
                        && Objects.equals(this.id, that.id)
                        && Objects.equals(this.name, that.name)
                        && Objects.equals(this.email, that.email)
                        && Objects.equals(this.caloriesPerDay, that.caloriesPerDay)
                        && Objects.equals(this.enabled, that.enabled)
                        && (new ArrayList<>(this.meals).equals(new ArrayList<>(that.meals)));
            }
        }
    }
}