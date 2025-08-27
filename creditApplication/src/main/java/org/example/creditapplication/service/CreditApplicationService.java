package org.example.creditapplication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.creditapplication.dto.ApplicationStatus;
import org.example.creditapplication.dto.CreditApplicationRequest;
import org.example.creditapplication.event.CreditApplicationEvent;
import org.example.creditapplication.model.CreditApplicationEntity;
import org.example.creditapplication.model.Status;
import org.example.creditapplication.repo.CreditApplicationRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditApplicationService {

    private final CreditApplicationRepo repository;
    private final KafkaTemplate<String, CreditApplicationEvent> kafkaTemplate;

    public Long createApplication(CreditApplicationRequest request) {
        CreditApplicationEntity creditApplication = new CreditApplicationEntity();
        BeanUtils.copyProperties(request, creditApplication);

        creditApplication = repository.save(creditApplication);

        CreditApplicationEvent creditApplicationEvent = new CreditApplicationEvent(
                creditApplication.getId(),
                creditApplication.getAmount(),
                creditApplication.getIncome()
        );

        kafkaTemplate.send("credit-applications", creditApplicationEvent);
        log.info("Sent credit application event: {}", creditApplicationEvent);

        return creditApplication.getId();
    }

    public ApplicationStatus getApplication(Long id) {
        CreditApplicationEntity creditApplication = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));
        
        return new ApplicationStatus(creditApplication.getId(), creditApplication.getStatus());
    }

    public void updateApplicationStatus(Long applicationId, Status status) {
        CreditApplicationEntity creditApplication = repository.findById(applicationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));
        
        creditApplication.setStatus(status);
        repository.save(creditApplication);
        log.info("Updated application {} status to {}", applicationId, status);
    }
}
