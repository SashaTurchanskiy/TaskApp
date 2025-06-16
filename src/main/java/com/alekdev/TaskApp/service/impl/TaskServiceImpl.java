package com.alekdev.TaskApp.service.impl;

import com.alekdev.TaskApp.dto.Response;
import com.alekdev.TaskApp.dto.TaskRequest;
import com.alekdev.TaskApp.entity.Task;
import com.alekdev.TaskApp.entity.User;
import com.alekdev.TaskApp.exception.NotFoundException;
import com.alekdev.TaskApp.repo.TaskRepo;
import com.alekdev.TaskApp.service.TaskService;
import com.alekdev.TaskApp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public Response<Void> deleteTask(Long taskId) {
        return null;
    }

    @Override
    public Response<List<Task>> getMyTasksByCompletionStatus(boolean completed) {
        return null;
    }

    @Override
    public Response<List<Task>> getMyTasksByPriority(String priority) {
        return null;
    }
}
