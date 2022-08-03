package org.example.camunda.process.solution.facade;

import java.io.IOException;
import java.util.List;

import org.example.camunda.process.solution.facade.dto.Form;
import org.example.camunda.process.solution.security.IsAdmin;
import org.example.camunda.process.solution.service.FormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.camunda.tasklist.exception.TaskListException;

@RestController
@RequestMapping("/edition/forms")
@CrossOrigin(origins = "*")
public class FormsEditionController extends AbstractController {

  private final Logger logger = LoggerFactory.getLogger(FormsEditionController.class);

  @Autowired private FormService formService;

  @IsAdmin
  @PostMapping
  public ResponseEntity<Form> save(@RequestBody Form form) throws IOException {
      formService.saveForm(form);
    return new ResponseEntity<>(form, HttpStatus.CREATED);
  }

  @IsAdmin
  @GetMapping("/{formKey}")
  @ResponseBody
  public Form getForm(@PathVariable String formKey)
      throws TaskListException, IOException {
    return formService.findByName(formKey);
  }
  
  @IsAdmin
  @DeleteMapping("/{formKey}")
  public void deleteForm(@PathVariable String formKey)
      throws TaskListException, IOException {
    formService.deleteByName(formKey);
  }
  
  
  @IsAdmin
  @GetMapping(value = "/names")
  public List<String> formNames() {
      getAuthenticatedUser();
    return formService.findNames();
  }

  @Override
  public Logger getLogger() {
    return logger;
  }
}
