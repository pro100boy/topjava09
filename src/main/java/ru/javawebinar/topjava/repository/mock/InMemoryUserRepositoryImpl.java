package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    /*{
        MealsUtil.USERS.forEach(this::save);
    }*/

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        return (repository.values().isEmpty() ?
                Collections.emptyList() :
                repository.values().stream().sorted(Comparator.comparing(User::getName)).collect(Collectors.toList()));
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);

        return repository.values().stream()
                //.peek(num -> System.out.println("will filter " + num))
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst().orElse(null);
                //.get();
    }

    public static void main(String[] args) {
        InMemoryUserRepositoryImpl inMemoryUserRepository = new InMemoryUserRepositoryImpl();

        /*System.out.println();
        inMemoryUserRepository.repository.entrySet().forEach(System.out::println);*/

        /*System.out.println();
        System.out.println(inMemoryUserRepository.delete(2));
        System.out.println(inMemoryUserRepository.delete(1));
        System.out.println(inMemoryUserRepository.delete(5));

        System.out.println();
        inMemoryUserRepository.repository.entrySet().forEach(System.out::println);*/

        User u = inMemoryUserRepository.repository.get(1);
        u.setEmail("wsewseh");
        inMemoryUserRepository.save(u);

        System.out.println();
        inMemoryUserRepository.repository.entrySet().forEach(System.out::println);

        //System.out.println();
        //inMemoryUserRepository.delete(1);
        //System.out.println(inMemoryUserRepository.get(1));

        System.out.println();
        System.out.println(inMemoryUserRepository.getByEmail("EMail3333@email.com"));
        System.out.println(inMemoryUserRepository.getByEmail("EMail3@email.com"));
    }
}
