package org.example.camunda.process.solution.facade;

import io.camunda.tasklist.dto.TaskState;
import io.camunda.tasklist.exception.TaskListException;
import io.camunda.zeebe.client.ZeebeClient;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.example.camunda.process.solution.facade.dto.Task;
import org.example.camunda.process.solution.model.TaskToken;
import org.example.camunda.process.solution.security.HasTasklist;
import org.example.camunda.process.solution.security.IsAdmin;
import org.example.camunda.process.solution.service.TaskListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController extends AbstractController {

  private static final Logger LOG = LoggerFactory.getLogger(TaskController.class);

  @Autowired private TaskListService taskListService;

  @Autowired private ZeebeClient zeebe;

  private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

  @HasTasklist
  @GetMapping()
  public List<Task> getTasks() throws TaskListException {
    return taskListService.getTasks(null, null);
  }

  @HasTasklist
  @GetMapping("/token/{token}")
  public Task tokenTask(@PathVariable String token) throws TaskListException {
    TaskToken taskToken = taskListService.retrieveToken(token);

    return taskListService.getTask(taskToken.getTaskId());
  }

  @HasTasklist
  @GetMapping("/unassigned")
  public List<Task> getUnassignedTasks() throws TaskListException {
    return taskListService.getTasks(TaskState.CREATED, null);
  }

  @HasTasklist
  @GetMapping("/myArchivedTasks")
  public List<Task> getCompletedTasks() throws TaskListException {
    String username = getAuthenticatedUser().getUsername();
    return taskListService.getAssigneeTasks(username, TaskState.COMPLETED, null);
  }

  @HasTasklist
  @GetMapping("/myOpenedTasks")
  public List<Task> getOpenedTasks() throws TaskListException {
    String username = getAuthenticatedUser().getUsername();
    return taskListService.getAssigneeTasks(username, TaskState.CREATED, null);
  }

  @HasTasklist
  @GetMapping("/groupTasks/{group}")
  public List<Task> getGroupTasks(@PathVariable String group) throws TaskListException {
    return taskListService.getGroupTasks(group, TaskState.CREATED, null);
  }

  @HasTasklist
  @GetMapping("/{taskId}/claim")
  public Task claimTask(@PathVariable String taskId) throws TaskListException {
    String username = getAuthenticatedUser().getUsername();
    return taskListService.claim(taskId, username);
  }

  @IsAdmin
  @GetMapping("/{taskId}/assign/{userId}")
  public Task assignTask(@PathVariable String taskId, @PathVariable String userId)
      throws TaskListException {
    return taskListService.claim(taskId, userId);
  }

  @HasTasklist
  @GetMapping("/{taskId}/unclaim")
  public Task unclaimTask(@PathVariable String taskId) throws TaskListException {
    return taskListService.unclaim(taskId);
  }

  @HasTasklist
  @PostMapping("/{taskId}")
  public void completeTask(@PathVariable String taskId, @RequestBody Map<String, Object> variables)
      throws TaskListException {

    LOG.info("Completing task " + taskId + "` with variables: " + variables);

    if (variables.containsKey("lastComment")) {
      String comment = (String) variables.get("lastComment");
      Object commentsVar = variables.get("comments");
      List<Map<String, String>> comments = null;
      Map<String, String> commentToAdd =
          Map.of(
              "author",
              getAuthenticatedUser().getUsername(),
              "comment",
              comment,
              "date",
              sdf.format(new Date()));
      if (commentsVar == null
          || (commentsVar instanceof String && "".equals((String) commentsVar))) {
        comments = List.of(commentToAdd);
      } else {
        comments = (List<Map<String, String>>) commentsVar;
        comments.add(commentToAdd);
      }

      variables.put("comments", comments);
    }

    taskListService.completeTask(taskId, variables);
  }

  @PostMapping("/adhoc")
  public void adhocTask(@RequestBody Map<String, String> variables) {
    zeebe
        .newPublishMessageCommand()
        .messageName("Msg_AdHocTaskNeeded")
        .correlationKey(variables.get("initiator"))
        .variables(variables)
        .send();
  }

  @Override
  public Logger getLogger() {
    return LOG;
  }
}
