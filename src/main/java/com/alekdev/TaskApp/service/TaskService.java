package com.alekdev.TaskApp.service;

import com.alekdev.TaskApp.dto.Response;
import com.alekdev.TaskApp.dto.TaskRequest;
import com.alekdev.TaskApp.entity.Task;

import java.util.List;

public interface TaskService {

    Response<Task> createTask(TaskRequest taskRequest);
    Response<List<Task>> getAllMyTasks();
    Response<Task> getTaskById(Long taskId);
    Response<Task> updateTask(TaskRequest taskRequest);
    Response<Void> deleteTask(Long taskId);
    Response<List<Task>> getMyTasksByCompletionStatus(boolean completed);
    Response<List<Task>> getMyTasksByPriority(String priority);

}
