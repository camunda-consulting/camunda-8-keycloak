package org.example.camunda.process.solution.worker;

import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;

import org.example.camunda.process.solution.ProcessVariables;
import org.example.camunda.process.solution.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.ZeebeVariable;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;

@Component
public class EmailWorker {
    
    private final static Logger LOG = LoggerFactory.getLogger(EmailWorker.class);
    
    @Autowired
    private MailService mailService;

    @ZeebeWorker(type = "email", autoComplete = true, forceFetchAllVariables = true)
    public ProcessVariables sendEmail(
        ActivatedJob job,
        @ZeebeVariable String to,
        @ZeebeVariable String cc,
        @ZeebeVariable String bcc,
        @ZeebeVariable String subject,
        @ZeebeVariable String template,
        @ZeebeVariable String locale) throws MessagingException, IOException {
        LOG.info("Sending email to " + to + " and bcc " + bcc + " using template " + template + " and subject: " + subject);

        Map<String, Object> variables = job.getVariablesAsMap();

        mailService.sendMail(to, cc, bcc, subject, template, locale, variables);

        return new ProcessVariables();
    }

}
