package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentRuleHibernate {

    private final SessionFactory sf;

    public Optional<Rule> findById(int id) {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("FROM Rule WHERE id = :fId", Rule.class)
                    .setParameter("fId", id)
                    .uniqueResultOptional();
        }
    }

    public Set<Rule> findByMultipleIds(int[] ids) {
        List<Integer> listIds = Arrays.stream(ids).boxed().toList();
        try (Session session = sf.openSession()) {
            List<Rule> listRule = session
                    .createQuery("FROM Rule WHERE id IN (:ruleIds)", Rule.class)
                    .setParameterList("ruleIds", listIds)
                    .list();
            return new HashSet<>(listRule);
        }
    }

    public Set<Rule> findAll() {
        try (Session session = sf.openSession()) {
            List<Rule> listRule = session
                    .createQuery("FROM Rule", Rule.class)
                    .list();
            return new HashSet<>(listRule);
        }
    }

    public void create(Rule rule) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(rule);
            session.getTransaction().commit();
        }
    }
}
