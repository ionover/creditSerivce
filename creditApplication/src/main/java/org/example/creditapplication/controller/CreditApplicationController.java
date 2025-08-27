package org.example.creditapplication.controller;

import org.example.creditapplication.CreditApplication;
import org.example.creditapplication.dto.CreditApplicationRequest;
import org.example.creditapplication.dto.CreditApplicationResponse;
import org.example.creditapplication.service.CreditApplicationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credit-applications")

public class CreditApplicationController {
    private CreditApplicationService creditApplicationService;

    @PostMapping
    public CreditApplicationResponse createCreditApplication(@RequestBody CreditApplicationRequest request) {
        Long id = creditApplicationService.createApplication(request);

        return new CreditApplicationResponse(id);
    }


    @GetMapping("/{id}/status")
    public ApplicationStatus getApplicationStatus(@PathVariable Long id){
        return creditApplicationService.getApplication(id);
    }
}
