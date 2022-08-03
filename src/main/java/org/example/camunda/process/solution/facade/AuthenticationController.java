package org.example.camunda.process.solution.facade;

import org.example.camunda.process.solution.facade.dto.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController extends AbstractController {

  private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

  @GetMapping("/me")
  public AuthUser getConnectedUser() {
    return getAuthenticatedUser();
  }

  @Override
  public Logger getLogger() {
    return logger;
  }
}
