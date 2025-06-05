package com.alekdev.TaskApp.dto;

import com.alekdev.TaskApp.enums.Priority;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequest {

    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;

    @Size(max = 500, message = "Title cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Completion status cannot be null")
    private Boolean completed;

    @NotNull(message = "Priority cannot be null")
    private Priority priority;

    @FutureOrPresent(message = "Due date must be in the present or future")
    private LocalDateTime dueDate;
}
