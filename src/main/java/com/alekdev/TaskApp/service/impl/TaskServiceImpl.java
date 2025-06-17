package com.alekdev.TaskApp.service.impl;

import com.alekdev.TaskApp.dto.Response;
import com.alekdev.TaskApp.dto.TaskRequest;
import com.alekdev.TaskApp.entity.Task;
import com.alekdev.TaskApp.entity.User;
import com.alekdev.TaskApp.enums.Priority;
import com.alekdev.TaskApp.exception.NotFoundException;
import com.alekdev.TaskApp.repo.TaskRepo;
import com.alekdev.TaskApp.service.TaskService;
import com.alekdev.TaskApp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;
    private final UserService userService;

    @Override
    public Response<Task> createTask(TaskRequest taskRequest) {
        log.info("Inside createTask()");

        User user = userService.getCurrentLoggedInUser();

        Task taskToSave = Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .completed(taskRequest.getCompleted())
                .priority(taskRequest.getPriority())
                .doeDate(taskRequest.getDueDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)
                .build();

        Task savedTask = taskRepo.save(taskToSave);

        return Response.<Task>builder()
                .statusCode(200)
                .message("Task created successfully")
                .data(savedTask)
                .build();
    }

    @Override
    @Transactional
    public Response<List<Task>> getAllMyTasks() {
        log.info("Fetching all tasks for the current user");

        User currentUser = userService.getCurrentLoggedInUser();

        List<Task> tasks = taskRepo.findByUser(currentUser, Sort.by(Sort.Direction.DESC, "id"));

        return Response.<List<Task>>builder()
                .statusCode(200)
                .message("Tasks retrieved successfully")
                .data(tasks)
                .build();
    }

    @Override
    public Response<Task> getTaskById(Long taskId) {
        Task task = taskRepo.findById(taskId).orElseThrow(()-> new NotFoundException("Task not found with id: " + taskId));
        return Response.<Task>builder()
                .statusCode(200)
                .message("Task retrieved successfully")
                .data(task)
                .build();
    }

    @Override
    public Response<Task> updateTask(TaskRequest taskRequest) {
        log.info("Updating task with id: {}", taskRequest.getId());

        Task task = taskRepo.findById(taskRequest.getId()).orElseThrow(()-> new NotFoundException("Task not found with id: " + taskRequest.getId()));

        if (taskRequest.getTitle() != null) task.setTitle(taskRequest.getTitle());
        if (taskRequest.getDescription() != null) task.setDescription(taskRequest.getDescription());
        if (taskRequest.getCompleted() != null) task.setCompleted(taskRequest.getCompleted());
        if (taskRequest.getPriority() != null) task.setPriority(taskRequest.getPriority());
        if (taskRequest.getDueDate() != null) task.setDoeDate(taskRequest.getDueDate());
        task.setUpdatedAt(LocalDateTime.now());

        // Save the updated task
        Task updatedTask = taskRepo.save(task);

        return Response.<Task>builder()
                .statusCode(200)
                .message("Task updated successfully")
                .data(updatedTask)
                .build();
    }

    @Override
    public Response<Void> deleteTask(Long taskId) {
        log.info("Deleting task with id: {}", taskId);

        if (!taskRepo.existsById(taskId)) {
            throw new NotFoundException("Task not found with id: " + taskId);
        }
        taskRepo.deleteById(taskId);
        return Response.<Void>builder()
                .statusCode(200)
                .message("Task deleted successfully")
                .build();
    }

    @Override
    @Transactional
    public Response<List<Task>> getMyTasksByCompletionStatus(boolean completed) {
        log.info("inside getMyTasksByCompletionStatus()");

        User currentUser = userService.getCurrentLoggedInUser();
        List<Task> tasks = taskRepo.findByCompletedAndUser(completed, currentUser);

        return Response.<List<Task>>builder()
                .statusCode(200)
                .message("Tasks filtered by completion status retrieved successfully")
                .data(tasks)
                .build();
    }

    @Override
    public Response<List<Task>> getMyTasksByPriority(String priority) {
        log.info("inside getMyTasksByPriority()");

        User currentUser = userService.getCurrentLoggedInUser();

        Priority priorityEnum = Priority.valueOf(priority.toUpperCase());
        List<Task> tasks = taskRepo.findByPriorityAndUser(priorityEnum, currentUser, Sort.by(Sort.Direction.DESC, "id"));

        return Response.<List<Task>>builder()
                .statusCode(200)
                .message("Tasks filtered by priority retrieved successfully")
                .data(tasks)
                .build();
    }
}
