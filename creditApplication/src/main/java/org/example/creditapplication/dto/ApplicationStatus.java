package org.example.creditapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.creditapplication.model.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStatus {
    private Long id;
    private Status status;
}