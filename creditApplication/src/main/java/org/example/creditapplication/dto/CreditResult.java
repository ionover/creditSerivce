package org.example.creditapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditResult {
    private Long applicationId;
    private String status; // SUCCESS или FAILED
}