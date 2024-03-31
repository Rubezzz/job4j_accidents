package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeHibernate {

    private final SessionFactory sf;

    public Optional<AccidentType> findById(int id) {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("FROM AccidentType WHERE id = :fId", AccidentType.class)
                    .setParameter("fId", id)
                    .uniqueResultOptional();
        }
    }

    public List<AccidentType> findAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("FROM AccidentType", AccidentType.class)
                    .list();
        }
    }

    public void create(AccidentType accidentType) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(accidentType);
            session.getTransaction().commit();
        }
    }
}
