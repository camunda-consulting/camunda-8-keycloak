package org.example.camunda.process.solution;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.MultilineRecursiveToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonInclude(Include.NON_NULL)
public class ProcessVariables {

  private String requestType;
  private String subject;
  private String initialMessage;
  private String date;
  private String lastComment;
  private String lastAdhocTaskType;
  private List<Map<String, String>> comments;
  private Map<String, Object> attachment;
  private String actionResult;
  private Boolean resolved;

  public String getRequestType() {
    return requestType;
  }

  public ProcessVariables setRequestType(String requestType) {
    this.requestType = requestType;
    return this;
  }

  public String getSubject() {
    return subject;
  }

  public ProcessVariables setSubject(String subject) {
    this.subject = subject;
    return this;
  }

  public String getInitialMessage() {
    return initialMessage;
  }

  public ProcessVariables setInitialMessage(String intialMessage) {
    this.initialMessage = intialMessage;
    return this;
  }

  public String getDate() {
    return date;
  }

  public ProcessVariables setDate(String date) {
    this.date = date;
    return this;
  }

  public String getLastComment() {
    return lastComment;
  }

  public ProcessVariables setLastComment(String lastComment) {
    this.lastComment = lastComment;
    return this;
  }

  public List<Map<String, String>> getComments() {
    return comments;
  }

  public ProcessVariables setComments(List<Map<String, String>> comments) {
    this.comments = comments;
    return this;
  }

  public Map<String, Object> getAttachment() {
    return attachment;
  }

  public ProcessVariables setAttachment(Map<String, Object> attachment) {
    this.attachment = attachment;
    return this;
  }

  public String getActionResult() {
    return actionResult;
  }

  public ProcessVariables setActionResult(String actionResult) {
    this.actionResult = actionResult;
    return this;
  }

  public Boolean getResolved() {
    return resolved;
  }

  public ProcessVariables setResolved(Boolean resolved) {
    this.resolved = resolved;
    return this;
  }

  public String getLastAdhocTaskType() {
    return lastAdhocTaskType;
  }

  public ProcessVariables setLastAdhocTaskType(String lastAdhocTaskType) {
    this.lastAdhocTaskType = lastAdhocTaskType;
    return this;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(
        this,
        new MultilineRecursiveToStringStyle() {
          public ToStringStyle withShortPrefixes() {
            this.setUseShortClassName(true);
            this.setUseIdentityHashCode(false);
            return this;
          }
        }.withShortPrefixes());
  }
}
