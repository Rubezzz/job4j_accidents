package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeService {

    private AccidentTypeRepository repository;

    public Optional<AccidentType> findById(int id) {
        return repository.findById(id);
    }

    public List<AccidentType> findAll() {
        return (List<AccidentType>) repository.findAll();
    }

    public void create(AccidentType accidentType) {
        repository.save(accidentType);
    }
}
