package ru.job4j.accidents.repository;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@NoArgsConstructor
public class AccidentMem {

    private Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private AtomicInteger index = new AtomicInteger(0);

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }

    public void create(Accident accident) {
        accident.setId(index.getAndIncrement());
        accidents.put(accident.getId(), accident);
    }

    public void save(Accident accident) {
        accidents.put(accident.getId(), accident);
    }
}
