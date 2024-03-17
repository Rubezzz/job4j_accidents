package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccidentMem {

    private Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public AccidentMem() {
        Accident accident1 = new Accident(0, "ДТП", "Дтп на ул.Ленина", "Ленина, 12");
        Accident accident2 = new Accident(1, "Парковка", "Неправильная парковка", "Космонавтов, 143");
        accidents.put(accident1.getId(), accident1);
        accidents.put(accident2.getId(), accident2);
    }

    public Accident get(int id) {
        return accidents.get(id);
    }

    public List<Accident> getAll() {
        return new ArrayList<>(accidents.values());
    }
}
