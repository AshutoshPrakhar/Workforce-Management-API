package com.company.workforcemgmt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Long id;
    private String title;
    private TaskStatus status;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Staff staff;
    private Priority priority = Priority.MEDIUM;
    private List<String> activityHistory = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
}
