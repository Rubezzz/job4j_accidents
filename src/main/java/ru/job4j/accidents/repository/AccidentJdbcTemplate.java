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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return jdbc.query(
                "SELECT a.id, a.name, a.text, a.address, t.id t_id, t.name t_name, r.id r_id, r.name r_name "
                        + "FROM accident a "
                        + "LEFT JOIN type t ON a.type_id = t.id "
                        + "LEFT JOIN accident_rule ar ON a.id = ar.accident_id "
                        + "LEFT JOIN rule r ON ar.rule_id = r.id",
                rs -> {
                    List<Accident> list = new ArrayList<>();
                    int prevId = -1;
                    Accident prevAccident = null;
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        if (prevId != id) {
                            Accident accident = new Accident();
                            accident.setId(id);
                            accident.setName(rs.getString("name"));
                            accident.setText(rs.getString("text"));
                            accident.setAddress(rs.getString("address"));
                            AccidentType type = new AccidentType(rs.getInt("t_id"), rs.getString("t_name"));
                            accident.setType(type);
                            if (rs.getString("r_id") != null) {
                                accident.getRules().add(new Rule(rs.getInt("r_id"), rs.getString("r_name")));
                            }
                            list.add(accident);
                            prevAccident = accident;
                            prevId = id;
                        } else {
                            prevAccident.getRules().add(new Rule(rs.getInt("r_id"), rs.getString("r_name")));
                        }
                    }
                    return list;
                }
        );
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