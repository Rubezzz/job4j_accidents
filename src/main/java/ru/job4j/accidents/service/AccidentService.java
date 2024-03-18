package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.List;

@Service
@AllArgsConstructor
public class AccidentService {

    private AccidentMem repository;

    public Accident findById(int id) {
        return repository.findById(id);
    }

    public List<Accident> findAll() {
        return repository.findAll();
    }
}
