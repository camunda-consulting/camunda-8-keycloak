package org.example.camunda.process.solution.worker;

import io.camunda.zeebe.spring.client.annotation.ZeebeVariablesAsType;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import org.example.camunda.process.solution.ProcessVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyWorker {

  private static final Logger LOG = LoggerFactory.getLogger(MyWorker.class);

  @ZeebeWorker(type = "cancelCard", autoComplete = true)
  public ProcessVariables cancelCard(@ZeebeVariablesAsType ProcessVariables variables) {
    LOG.info("Invoking cancelCard with variables: " + variables);
    int cardCanceled = (int) Math.round(Math.random());
    if (cardCanceled == 1) {
      return new ProcessVariables().setActionResult("Card successfully canceled");
    }
    return new ProcessVariables().setActionResult("Card couldn't be canceled");
  }

  @ZeebeWorker(type = "revertMovment", autoComplete = true)
  public ProcessVariables revertMovment(@ZeebeVariablesAsType ProcessVariables variables) {
    LOG.info("Invoking revertMovment with variables: " + variables);
    int cardCanceled = (int) Math.round(Math.random());
    if (cardCanceled == 1) {
      return new ProcessVariables().setActionResult("Movment successfully reverted");
    }
    return new ProcessVariables().setActionResult("Movment couldn't be canceled");
  }
}
