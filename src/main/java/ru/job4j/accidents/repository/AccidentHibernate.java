package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentHibernate {

    private final CrudRepository crudRepository;

    public Accident create(Accident accident) {
        crudRepository.run(session -> session.save(accident));
        return accident;
    }

    public void save(Accident accident) {
        crudRepository.run(session -> session.update(accident));
    }

    public List<Accident> findAll() {
        return crudRepository.query(
                "SELECT DISTINCT a FROM Accident a LEFT JOIN FETCH a.rules",
                Accident.class
        );
    }

    public Optional<Accident> findById(int id) {
        return crudRepository.optional(
                "FROM Accident WHERE id = :fId",
                Accident.class,
                Map.of("fId", id)
        );
    }
}