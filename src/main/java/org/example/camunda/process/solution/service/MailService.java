package org.example.camunda.process.solution.service;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

import io.camunda.google.GmailUtils;
import io.camunda.google.model.Mail;
import io.camunda.google.thymeleaf.MailBuilderUtils;

@Service
public class MailService {
    
    public void sendMail(String to, String cc, String bcc, String subject, String templateName, String locale, Map<String, Object> variables) throws MessagingException, IOException {
        String htmlBody = MailBuilderUtils.buildMailBody(templateName, variables, Locale.forLanguageTag(locale));
        Mail mail = new Mail.Builder().to(to).subject(subject).body(htmlBody).build();
 
        GmailUtils.sendEmail(mail); 
    }
    
}
