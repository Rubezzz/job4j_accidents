package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentTypeMem {

    private Map<Integer, AccidentType> accidentTypes = new ConcurrentHashMap<>();
    private AtomicInteger index = new AtomicInteger(0);

    public AccidentTypeMem() {
        create(new AccidentType(0, "Две машины"));
        create(new AccidentType(0, "Машина и человек"));
        create(new AccidentType(0, "Машина и велосипед"));
    }

    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(accidentTypes.get(id));
    }

    public List<AccidentType> findAll() {
        return new ArrayList<>(accidentTypes.values());
    }

    public void create(AccidentType accidentType) {
        accidentType.setId(index.getAndIncrement());
        accidentTypes.put(accidentType.getId(), accidentType);
    }
}
