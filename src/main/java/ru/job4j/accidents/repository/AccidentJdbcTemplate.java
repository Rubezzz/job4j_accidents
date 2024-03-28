package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {

    private final JdbcTemplate jdbc;

    public Accident create(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO accident(name, text, address, type_id) VALUES (?, ?, ?, ?);",
                    new String[]{"id"});
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, keyHolder);
        for (Rule rule : accident.getRules()) {
            jdbc.update(
                    "INSERT INTO accident_rule(accident_id, rule_id) VALUES (?, ?);",
                    keyHolder.getKey(),
                    rule.getId()
            );
        }
        return accident;
    }

    public void save(Accident accident) {
        jdbc.update(
                "UPDATE accident SET name = ?, text = ?, address = ?, type_id = ? WHERE id = ?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId()
        );
        jdbc.update("DELETE FROM accident_rule WHERE accident_id = ?", accident.getId());
        for (Rule rule : accident.getRules()) {
            jdbc.update(
                    "INSERT INTO accident_rule(accident_id, rule_id) VALUES (?, ?);",
                    accident.getId(),
                    rule.getId()
            );
        }
    }

    public List<Accident> findAll() {
        List<Accident> rsl = jdbc.query(
                "SELECT a.id, a.name, a.text, a.address, t.id t_id, t.name t_name "
                + "FROM accident a, type t "
                + "WHERE a.type_id = t.id",
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    AccidentType type = new AccidentType(rs.getInt("t_id"), rs.getString("t_name"));
                    accident.setType(type);
                    return accident;
                }
        );
        for (Accident accident : rsl) {
            List<Rule> listRules = jdbc.query(
                    "SELECT id, name FROM rule WHERE id IN "
                    + "(SELECT rule_id FROM accident_rule WHERE accident_id = ?)",
                    (rs, row) -> {
                        Rule rule = new Rule();
                        rule.setId(rs.getInt("id"));
                        rule.setName(rs.getString("name"));
                        return rule;
                    },
                    accident.getId()
            );
            Set<Rule> setRules = new HashSet<>(listRules);
            accident.setRules(setRules);
        }
        return rsl;
    }

    public Optional<Accident> findById(int id) {
        Accident rsl = jdbc.queryForObject(
                "SELECT a.id, a.name, a.text, a.address, t.id t_id, t.name t_name "
                + "FROM accident a, type t "
                + "WHERE a.type_id = t.id AND a.id = ?",
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    AccidentType type = new AccidentType(rs.getInt("t_id"), rs.getString("t_name"));
                    accident.setType(type);
                    return accident;
                },
                id
        );
        return Optional.ofNullable(rsl);
    }
}