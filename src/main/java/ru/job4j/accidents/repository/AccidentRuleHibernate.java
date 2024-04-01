package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentRuleHibernate {

    private final CrudRepository crudRepository;

    public Optional<Rule> findById(int id) {
        return crudRepository.optional(
                "FROM Rule WHERE id = :fId",
                Rule.class,
                Map.of("fId", id)
        );
    }

    public Set<Rule> findByMultipleIds(int[] ids) {
        List<Integer> listIds = Arrays.stream(ids).boxed().toList();
        List<Rule> listRule = crudRepository.query(
                "FROM Rule WHERE id IN (:ruleIds)",
                Rule.class,
                Map.of("ruleIds", listIds));
        return new HashSet<>(listRule);
    }

    public Set<Rule> findAll() {
        return new HashSet<>(crudRepository.query("FROM Rule", Rule.class, Map.of()));
    }

    public void create(Rule rule) {
        crudRepository.run(session -> session.save(rule));
    }
}
