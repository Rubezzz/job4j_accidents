package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeHibernate {

    private final CrudRepository crudRepository;

    public Optional<AccidentType> findById(int id) {
        return crudRepository.optional(
                "FROM AccidentType WHERE id = :fId",
                AccidentType.class,
                Map.of("fId", id)
        );
    }

    public List<AccidentType> findAll() {
        return crudRepository.query("FROM AccidentType", AccidentType.class, Map.of());
    }

    public void create(AccidentType accidentType) {
        crudRepository.run(session -> session.save(accidentType));
    }
}
