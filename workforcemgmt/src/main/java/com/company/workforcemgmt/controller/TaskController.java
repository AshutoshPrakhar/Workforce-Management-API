package com.company.workforcemgmt.controller;

import com.company.workforcemgmt.model.Priority;
import com.company.workforcemgmt.model.Staff;
import com.company.workforcemgmt.model.Task;
import com.company.workforcemgmt.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // ----- STAFF -----

    @PostMapping("/staff")
    public Staff createStaff(@RequestParam String name) {
        return taskService.createStaff(name);
    }

    @GetMapping("/staff/{id}")
    public Staff getStaff(@PathVariable Long id) {
        return taskService.getStaff(id).orElseThrow(() -> new RuntimeException("Staff not found"));
    }

    // ----- TASKS -----

    @PostMapping("/tasks")
    public Task createTask(
            @RequestParam String title,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate,
            @RequestParam Long staffId
    ) {
        return taskService.createTask(title, startDate, dueDate, staffId);
    }

    @GetMapping("/tasks")
    public List<Task> getTasksBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        return taskService.getTasksBetween(start, end);
    }

    @GetMapping("/tasks/smart")
    public List<Task> getSmartDailyTasks(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        return taskService.getSmartDailyTasks(start, end);
    }

    @GetMapping("/tasks/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTask(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @PutMapping("/tasks/{id}/cancel")
    public void cancelTask(@PathVariable Long id) {
        taskService.cancelTask(id);
    }

    @PutMapping("/tasks/{id}/reassign")
    public Task reassignTask(@PathVariable Long id, @RequestParam Long newStaffId) {
        return taskService.reassignTask(id, newStaffId);
    }

    @PutMapping("/tasks/{id}/priority")
    public Task changePriority(@PathVariable Long id, @RequestParam Priority priority) {
        return taskService.changePriority(id, priority);
    }

    @GetMapping("/tasks/priority/{level}")
    public List<Task> getByPriority(@PathVariable String level) {
        return taskService.getTasksByPriority(level);
    }

    @PostMapping("/tasks/{id}/comment")
    public Task addComment(@PathVariable Long id, @RequestParam String author, @RequestParam String message) {
        return taskService.addComment(id, author, message);
    }
}
