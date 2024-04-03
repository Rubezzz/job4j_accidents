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

    public Set<Rule> findByMultipleIds(int[] ids) {
        return repository.findByIdIn(ids);
    }

    public Set<Rule> findAll() {
        return repository.findAll();
    }

    public void create(Rule rule) {
        repository.save(rule);
    }
}
