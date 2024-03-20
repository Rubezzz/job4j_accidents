package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private AtomicInteger index = new AtomicInteger(0);
    private List<AccidentType> types = new ArrayList<>();

    public AccidentMem() {
        types.add(new AccidentType(1, "Две машины"));
        types.add(new AccidentType(2, "Машина и человек"));
        types.add(new AccidentType(3, "Машина и велосипед"));
        create(new Accident(0, "ДТП", "Дтп на ул.Ленина", "Ленина, 12", types.get(1)));
        create(new Accident(0, "Парковка", "Неправильная парковка", "Космонавтов, 143", types.get(1)));
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }

    public void create(Accident accident) {
        accident.setId(index.getAndIncrement());
        accident.setType(types.get(accident.getType().getId()));
        accidents.put(accident.getId(), accident);
    }

    public void save(Accident accident) {
        accidents.put(accident.getId(), accident);
    }
}
