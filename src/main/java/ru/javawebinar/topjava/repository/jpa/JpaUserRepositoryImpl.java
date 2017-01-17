package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaUserRepositoryImpl implements UserRepository {

/*
    @Autowired
    private SessionFactory sessionFactory;

    private Session openSession() {
        return sessionFactory.getCurrentSession();
    }
*/

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public User save(User user) {
        if (user.isNew()) {
            em.persist(user);
            return user;
        } else {
            return em.merge(user);
        }
    }

    @Override
    public User get(int id) {
        return em.find(User.class, id);
    }

    @Override
    @Transactional
    public boolean delete(int id) {

/*      User ref = em.getReference(User.class, id);
        em.remove(ref);

        Query<User> query = em.createQuery("DELETE FROM User u WHERE u.id=:id");
        return query.setParameter("id", id).executeUpdate() != 0;
*/
        return em.createNamedQuery(User.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = em.createNamedQuery(User.BY_EMAIL, User.class).setParameter(1, email).getResultList();
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return em.createNamedQuery(User.ALL_SORTED, User.class).getResultList();
    }

    @Override
    public Collection<User> getAllWithMeals() {
        List<User> users = getAll();
        users.forEach(u->u.getMeals().size()); //дергаем прокси-объект (нахдимся в контексте сессии, поэтому можем себе это позволить)
        return users;
    }

    @Override
    public User getWithMeals(int id) {
        User user = get(id);
        user.getMeals().size(); //дергаем прокси-объект
        return user;
    }

    /*
    updateLazy работает без учета аннотаций, соответсвенно, при включенных cascade = CascadeType.ALL и orphanRemoval = true
    можно сохранять упрощенный объект User user (без подвязанного списка еды).
    Дополнительно в комментах для UserServiceTest_Hsqldb_Jpa.testUpdate()
     */
    @Override
    @Transactional
    public User updateLazy(User user){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaUpdate<User> query = builder.createCriteriaUpdate(User.class);
        Root<User> u = query.from(User.class);
        query.set("name", user.getName());
        query.set("email", user.getEmail());
        query.set("password", user.getPassword());
        query.set("enabled", user.isEnabled());
        query.set("registered", user.getRegistered());
        query.set("caloriesPerDay", user.getCaloriesPerDay());
        Predicate predicate = builder.equal(u.get("id"), user.getId());
        query.where(builder.and(predicate));
        if (em.createQuery(query).executeUpdate()==0) return null;
        return user;
    }
}
