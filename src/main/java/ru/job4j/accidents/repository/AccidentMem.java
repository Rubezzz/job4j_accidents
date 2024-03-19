package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private AtomicInteger index = new AtomicInteger(0);

    public AccidentMem() {
        create(new Accident(0, "ДТП", "Дтп на ул.Ленина", "Ленина, 12"));
        create(new Accident(0, "Парковка", "Неправильная парковка", "Космонавтов, 143"));
    }

    public Accident findById(int id) {
        return accidents.get(id);
    }

    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }

    public void create(Accident accident) {
        accident.setId(index.getAndIncrement());
        accidents.put(accident.getId(), accident);
    }
}
