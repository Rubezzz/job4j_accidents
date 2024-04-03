package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Rule;

import java.util.Set;

public interface AccidentRuleRepository extends CrudRepository<Rule, Integer> {

    Set<Rule> findAll();

    Set<Rule> findByIdIn(int[] ids);
}