package org.example.camunda.process.solution.facade;

import java.io.IOException;
import java.util.List;

import org.example.camunda.process.solution.facade.dto.FormJsListValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.camunda.tasklist.exception.TaskListException;

@RestController
@RequestMapping("/simul")
@CrossOrigin(origins = "*")
public class SimulationController {

    @GetMapping("/requestTypes")
    public List<FormJsListValue> requestTypes() throws TaskListException, IOException {

        return List.of(new FormJsListValue("cardLost", "Card lost or stolen"),
                new FormJsListValue("fraud", "Fraudulent movments"),
                new FormJsListValue("dbtCollection", "Debt collection"),
                new FormJsListValue("creditReport", "Credit report"),
                new FormJsListValue("moneyTransfer", "Money transfer"));

    }

    @GetMapping("/checklist")
    public List<FormJsListValue> getChecklist() {
        return List.of(new FormJsListValue("1", "choice 1"), new FormJsListValue("2", "choice 2"));
    }
}
