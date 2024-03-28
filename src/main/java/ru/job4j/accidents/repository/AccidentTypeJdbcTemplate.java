package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentTypeJdbcTemplate {

    private final JdbcTemplate jdbc;

    public Optional<AccidentType> findById(int id) {
        AccidentType rsl = jdbc.queryForObject(
                "SELECT name FROM type WHERE id = ?",
                (rs, row) -> {
                    AccidentType type = new AccidentType();
                    type.setId(id);
                    type.setName(rs.getString("name"));
                    return type;
                },
                id
        );
        return Optional.ofNullable(rsl);
    }

    public List<AccidentType> findAll() {
        return jdbc.query("SELECT id, name FROM type",
                (rs, row) -> {
                    AccidentType type = new AccidentType();
                    type.setId(rs.getInt("id"));
                    type.setName(rs.getString("name"));
                    return type;
                });
    }

    public void create(AccidentType accidentType) {
        jdbc.update("INSERT INTO type(name) VALUES (?);", accidentType.getName());
    }
}
