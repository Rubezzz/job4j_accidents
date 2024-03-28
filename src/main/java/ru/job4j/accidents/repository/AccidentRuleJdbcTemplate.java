package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class AccidentRuleJdbcTemplate {

    private final JdbcTemplate jdbc;
    private final NamedParameterJdbcTemplate namedParameterJdbc;

    public Optional<Rule> findById(int id) {
        Rule rsl = jdbc.queryForObject(
                "SELECT name FROM rule WHERE id = ?",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(id);
                    rule.setName(rs.getString("name"));
                    return rule;
                },
                id
        );
        return Optional.ofNullable(rsl);
    }

    public Set<Rule> findByMultipleIds(int[] ids) {
        List<Integer> listIds = Arrays.stream(ids).boxed().collect(Collectors.toList());
        List<Rule> ruleList = namedParameterJdbc.query(
                "SELECT id, name FROM rule WHERE id IN (:rules)",
                new MapSqlParameterSource("rules", listIds),
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                }
        );
        return new HashSet<>(ruleList);
    }

    public Set<Rule> findAll() {
        List<Rule> ruleList = jdbc.query("SELECT id, name FROM rule",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
        return new HashSet<>(ruleList);
    }

    public void create(Rule rule) {
        jdbc.update("INSERT INTO rule(name) VALUES (?);", rule.getName());
    }
}
