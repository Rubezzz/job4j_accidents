package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.*;

import java.util.*;

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
        return (List<Accident>) accidentRepository.findAll();
    }

    public void create(Accident accident) {
        Optional<AccidentType> accidentType = accidentTypeRepository.findById(accident.getType().getId());
        accidentType.ifPresent(accident::setType);
        accidentRepository.save(accident);
    }

    private void setRulesToAccident(Accident accident, String[] ruleIds) {
        if (ruleIds != null) {
            List<Integer> ids = Arrays.stream(ruleIds).map(Integer::parseInt).toList();
            Set<Rule> rulesSet = new HashSet<>();
            accidentRuleRepository.findAllById(ids).forEach(rulesSet::add);
            accident.setRules(rulesSet);
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
