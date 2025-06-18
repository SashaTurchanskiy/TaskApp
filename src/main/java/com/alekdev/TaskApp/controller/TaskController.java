package com.alekdev.TaskApp.controller;

import com.alekdev.TaskApp.dto.Response;
import com.alekdev.TaskApp.dto.TaskRequest;
import com.alekdev.TaskApp.entity.Task;
import com.alekdev.TaskApp.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Response<Task>> createTask(@Valid @RequestBody TaskRequest taskRequest){
        return ResponseEntity.ok(taskService.createTask(taskRequest));
    }
    @PutMapping
    public ResponseEntity<Response<Task>> updateTask(@Valid @RequestBody TaskRequest taskRequest){
        return ResponseEntity.ok(taskService.updateTask(taskRequest));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response<List<Task>>> getAllMyTasks() {
        return ResponseEntity.ok(taskService.getAllMyTasks());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteTask(@PathVariable Long id) {
       return ResponseEntity.ok(taskService.deleteTask(id));
    }
    @GetMapping("/status")
    public ResponseEntity<Response<List<Task>>> getMyTasksByCompletionStatus(@RequestParam boolean completed) {
        return ResponseEntity.ok(taskService.getMyTasksByCompletionStatus(completed));
    }
    @GetMapping("/priority")
    public ResponseEntity<Response<List<Task>>> getMyTasksByPriority(@RequestParam String priority) {
        return ResponseEntity.ok(taskService.getMyTasksByPriority(priority));
    }
}
