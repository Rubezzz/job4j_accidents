package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRuleRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class AccidentRuleService {

    private AccidentRuleRepository repository;

    public Optional<Rule> findById(int id) {
        return repository.findById(id);
    }

    private Set<Rule> iterableToSet(Iterable<Rule> rules) {
        Set<Rule> rulesSet = new HashSet<>();
        rules.forEach(rulesSet::add);
        return rulesSet;
    }

    public Set<Rule> findByMultipleIds(int[] ids) {
        List<Integer> listIds = Arrays.stream(ids).boxed().toList();
        return iterableToSet(repository.findAllById(listIds));
    }

    public Set<Rule> findAll() {
        return iterableToSet(repository.findAll());
    }

    public void create(Rule rule) {
        repository.save(rule);
    }
}
