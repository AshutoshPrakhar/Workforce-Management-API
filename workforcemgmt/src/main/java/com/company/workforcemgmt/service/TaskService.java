package com.company.workforcemgmt.service;

import com.company.workforcemgmt.model.*;
import com.company.workforcemgmt.repository.InMemoryTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final InMemoryTaskRepository repository;

    // ----------- Staff -----------

    public Staff createStaff(String name) {
        Staff staff = new Staff(null, name);
        return repository.saveStaff(staff);
    }

    public Optional<Staff> getStaff(Long id) {
        return repository.findStaffById(id);
    }

    // ----------- Task -----------

    public Task createTask(String title, LocalDate startDate, LocalDate dueDate, Long staffId) {
        Staff staff = repository.findStaffById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        Task task = new Task();
        task.setTitle(title);
        task.setStartDate(startDate);
        task.setDueDate(dueDate);
        task.setStaff(staff);
        task.setStatus(TaskStatus.ACTIVE);
        task.getActivityHistory().add("Task created and assigned to " + staff.getName());

        return repository.saveTask(task);
    }

    public List<Task> getTasksBetween(LocalDate start, LocalDate end) {
        return repository.getTasksByDateRange(start, end);
    }

    public List<Task> getSmartDailyTasks(LocalDate start, LocalDate end) {
        return repository.getSmartView(start, end);
    }

    public Optional<Task> getTask(Long id) {
        return repository.findTaskById(id);
    }

    public void cancelTask(Long id) {
        Task task = repository.findTaskById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(TaskStatus.CANCELLED);
        task.getActivityHistory().add("Task cancelled");
        repository.saveTask(task);
    }

    public Task reassignTask(Long taskId, Long newStaffId) {
        Task oldTask = repository.findTaskById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (oldTask.getStaff().getId().equals(newStaffId)) {
            return oldTask;
        }

        cancelTask(taskId); // mark old one as cancelled

        Staff newStaff = repository.findStaffById(newStaffId)
                .orElseThrow(() -> new RuntimeException("New staff not found"));

        Task newTask = new Task();
        newTask.setTitle(oldTask.getTitle());
        newTask.setStartDate(LocalDate.now());
        newTask.setDueDate(oldTask.getDueDate());
        newTask.setStaff(newStaff);
        newTask.setStatus(TaskStatus.ACTIVE);
        newTask.getActivityHistory().add("Task reassigned from " + oldTask.getStaff().getName() + " to " + newStaff.getName());

        return repository.saveTask(newTask);
    }

    public Task changePriority(Long taskId, Priority priority) {
        Task task = repository.findTaskById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setPriority(priority);
        task.getActivityHistory().add("Priority changed to " + priority.name());
        return repository.saveTask(task);
    }

    public List<Task> getTasksByPriority(String priority) {
        return repository.getTasksByPriority(priority);
    }

    public Task addComment(Long taskId, String author, String message) {
        Task task = repository.findTaskById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Comment comment = new Comment(author, message, java.time.LocalDateTime.now());
        task.getComments().add(comment);
        task.getActivityHistory().add("Comment added by " + author);
        return repository.saveTask(task);
    }
}
