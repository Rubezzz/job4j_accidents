package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.AccidentRuleRepository;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {

    private AccidentRepository accidentRepository;
    private AccidentTypeRepository accidentTypeRepository;
    private AccidentRuleRepository accidentRuleRepository;

    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    public void create(Accident accident) {
        Optional<AccidentType> accidentType = accidentTypeRepository.findById(accident.getType().getId());
        accidentType.ifPresent(accident::setType);
        accidentRepository.save(accident);
    }

    private void setRulesToAccident(Accident accident, String[] ruleIds) {
        if (ruleIds != null) {
            int[] ids = Arrays.stream(ruleIds).mapToInt(Integer::parseInt).toArray();
            accident.setRules(accidentRuleRepository.findByIdIn(ids));
        }
    }

    public void create(Accident accident, String[] ruleIds) {
        setRulesToAccident(accident, ruleIds);
        create(accident);
    }

    public void save(Accident accident) {
        accidentRepository.save(accident);
    }

    public void save(Accident accident, String[] ruleIds) {
        setRulesToAccident(accident, ruleIds);
        save(accident);
    }
}
