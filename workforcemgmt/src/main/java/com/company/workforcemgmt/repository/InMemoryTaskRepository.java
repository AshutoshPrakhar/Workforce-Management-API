package com.company.workforcemgmt.repository;

import com.company.workforcemgmt.model.Staff;
import com.company.workforcemgmt.model.Task;
import com.company.workforcemgmt.model.TaskStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryTaskRepository {
    private final Map<Long, Task> taskMap = new HashMap<>();
    private final Map<Long, Staff> staffMap = new HashMap<>();
    private final AtomicLong taskIdCounter = new AtomicLong(1);
    private final AtomicLong staffIdCounter = new AtomicLong(1);

    // ----- TASK Methods -----

    public Task saveTask(Task task) {
        if (task.getId() == null) {
            task.setId(taskIdCounter.getAndIncrement());
        }
        taskMap.put(task.getId(), task);
        return task;
    }
    public Optional<Task> findTaskById(Long id) {
        return Optional.ofNullable(taskMap.get(id));
    }
    public List<Task> getAllTasks() {
        return new ArrayList<>(taskMap.values());
    }
    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskMap.values().stream()
                .filter(t -> t.getStatus() == status)
                .collect(Collectors.toList());
    }
    public List<Task> getTasksByPriority(String priority) {
        return taskMap.values().stream()
                .filter(t -> t.getPriority().name().equalsIgnoreCase(priority))
                .collect(Collectors.toList());
    }
    public List<Task> getTasksByDateRange(LocalDate start, LocalDate end) {
        return taskMap.values().stream()
                .filter(t -> t.getStatus() != TaskStatus.CANCELLED)
                .filter(t -> {
                    LocalDate s = t.getStartDate();
                    return (s.isEqual(start) || s.isAfter(start)) &&
                            (s.isEqual(end) || s.isBefore(end));
                })
                .collect(Collectors.toList());
    }
    public List<Task> getSmartView(LocalDate start, LocalDate end) {
        return taskMap.values().stream()
                .filter(t -> t.getStatus() == TaskStatus.ACTIVE)
                .filter(t -> {
                    LocalDate s = t.getStartDate();
                    return (s.isEqual(start) || s.isAfter(start)) && (s.isBefore(end) || s.isEqual(end))
                            || s.isBefore(start); // include old active tasks
                })
                .collect(Collectors.toList());
    }

    // ----- STAFF Methods -----

    public Staff saveStaff(Staff staff) {
        if (staff.getId() == null) {
            staff.setId(staffIdCounter.getAndIncrement());
        }
        staffMap.put(staff.getId(), staff);
        return staff;
    }
    public Optional<Staff> findStaffById(Long id) {
        return Optional.ofNullable(staffMap.get(id));
    }
    public List<Staff> getAllStaff() {
        return new ArrayList<>(staffMap.values());
    }
}
