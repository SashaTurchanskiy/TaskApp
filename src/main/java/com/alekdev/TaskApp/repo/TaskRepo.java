package com.alekdev.TaskApp.repo;

import com.alekdev.TaskApp.entity.Task;
import com.alekdev.TaskApp.entity.User;
import com.alekdev.TaskApp.enums.Priority;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user, Sort sort);
    List<Task> findByCompletedAndUser(boolean completed, User user);
    List<Task> findByPriorityAndUser(Priority priority, User user, Sort sort);
}
