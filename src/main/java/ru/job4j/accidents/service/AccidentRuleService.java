package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRuleMem;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AccidentRuleService {

    private AccidentRuleMem repository;

    public Optional<Rule> findById(int id) {
        return repository.findById(id);
    }

    public Set<Rule> findByMultipleIds(int[] ids) {
        return repository.findByMultipleIds(ids);
    }

    public Set<Rule> findAll() {
        return repository.findAll();
    }

    public void create(Rule rule) {
        repository.create(rule);
    }
}
