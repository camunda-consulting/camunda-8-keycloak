package org.example.camunda.process.solution.worker;

import org.example.camunda.process.solution.ProcessVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.camunda.zeebe.spring.client.annotation.ZeebeVariablesAsType;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;

@Component
public class MyWorker {

  private static final Logger LOG = LoggerFactory.getLogger(MyWorker.class);

  @ZeebeWorker(type = "cancelCard", autoComplete = true)
  public ProcessVariables invokeMyService(@ZeebeVariablesAsType ProcessVariables variables) {
    LOG.info("Invoking myService with variables: " + variables);
    int cardCanceled = (int) Math.round(Math.random());
    if(cardCanceled==1) {
        return new ProcessVariables().setActionTaken("card successfully canceled");
    }
    return new ProcessVariables().setActionTaken("card couldn't be canceled");
  }
}
