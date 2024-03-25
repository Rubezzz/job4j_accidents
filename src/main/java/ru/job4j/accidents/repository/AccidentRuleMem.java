package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class AccidentRuleMem {

    private Map<Integer, Rule> accidentRule = new ConcurrentHashMap<>();
    private AtomicInteger index = new AtomicInteger(0);

    public AccidentRuleMem() {
        create(new Rule(0, "Статья. 1"));
        create(new Rule(0, "Статья. 2"));
        create(new Rule(0, "Статья. 3"));
    }

    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(accidentRule.get(id));
    }

    public Set<Rule> findByMultipleIds(int[] ids) {
        return accidentRule.values()
                .stream()
                .filter(rule -> IntStream.of(ids).anyMatch(id -> id == rule.getId()))
                .collect(Collectors.toSet());
    }

    public Optional<Rule> findByName(int id) {
        return Optional.ofNullable(accidentRule.get(id));
    }

    public Set<Rule> findAll() {
        return new HashSet<>(accidentRule.values());
    }

    public void create(Rule rule) {
        rule.setId(index.getAndIncrement());
        accidentRule.put(rule.getId(), rule);
    }
}
