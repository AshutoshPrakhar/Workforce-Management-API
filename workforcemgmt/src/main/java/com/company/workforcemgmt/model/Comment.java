package com.company.workforcemgmt.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Comment {
    private String author;
    private String message;
    private LocalDateTime timestamp;
}
