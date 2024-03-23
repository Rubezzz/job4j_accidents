package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentRuleService;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@AllArgsConstructor
public class AccidentController {

    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;
    private final AccidentRuleService accidentRuleService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        List<AccidentType> types = accidentTypeService.findAll();
        if (types.size() == 0) {
            model.addAttribute("message", "Типы инцидентов не найдены!");
            return "errors/404";
        }
        Set<Rule> rules = accidentRuleService.findAll();
        if (rules.size() == 0) {
            model.addAttribute("message", "Статьи инцидентов не найдены!");
            return "errors/404";
        }
        model.addAttribute("types", types);
        model.addAttribute("rules", rules);
        return "accident/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        Set<Rule> resultRules = new HashSet<>();
        if (ids != null) {
            for (String id : ids) {
                Optional<Rule> optionalRule = accidentRuleService.findById(Integer.parseInt(id));
                optionalRule.ifPresent(resultRules::add);
            }
        }
        accident.setRules(resultRules);
        accidentService.create(accident);
        return "redirect:/index";
    }

    @GetMapping("/formUpdateAccident")
    public String update(@RequestParam("id") int id, Model model) {
        Optional<Accident> accident = accidentService.findById(id);
        if (accident.isEmpty()) {
            model.addAttribute("message", "Инцидент не найден!");
            return "errors/404";
        }
        model.addAttribute("accident", accident.get());
        return "accident/updateAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident) {
        accidentService.save(accident);
        return "redirect:/";
    }
}
