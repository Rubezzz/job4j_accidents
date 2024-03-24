package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentMem;
import ru.job4j.accidents.repository.AccidentRuleMem;
import ru.job4j.accidents.repository.AccidentTypeMem;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AccidentService {

    private AccidentMem accidentRepository;
    private AccidentTypeMem accidentTypeRepository;
    private AccidentRuleMem accidentRuleRepository;

    public AccidentService(AccidentMem accidentMem, AccidentTypeMem accidentTypeMem, AccidentRuleMem accidentRuleMem) {
        this.accidentRepository = accidentMem;
        this.accidentTypeRepository = accidentTypeMem;
        this.accidentRuleRepository = accidentRuleMem;
        initAccidents();
    }

    private void initAccidents() {
        create(new Accident(0, "ДТП",
                "Дтп на ул.Ленина",
                "Ленина, 12",
                accidentTypeRepository.findById(0).get(),
                Set.of()));
        create(new Accident(0,
                "Парковка",
                "Неправильная парковка",
                "Космонавтов, 143",
                accidentTypeRepository.findById(0).get(),
                Set.of()));
    }

    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    public void create(Accident accident) {
        Optional<AccidentType> accidentType = accidentTypeRepository.findById(accident.getType().getId());
        accidentType.ifPresent(accident::setType);
        accidentRepository.create(accident);
    }

    public void create(Accident accident, int[] ruleIds) {
        accident.setRules(accidentRuleRepository.findByMultipleIds(ruleIds));
        create(accident);
    }

    public void save(Accident accident) {
        accidentRepository.save(accident);
    }
}
