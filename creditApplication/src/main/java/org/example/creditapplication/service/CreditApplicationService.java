package org.example.creditapplication.service;

import lombok.RequiredArgsConstructor;
import org.example.creditapplication.CreditApplication;
import org.example.creditapplication.dto.CreditApplicationRequest;
import org.example.creditapplication.event.CreditApplicationEvent;
import org.example.creditapplication.model.Status;
import org.example.creditapplication.repo.CreditApplicationRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CreditApplicationService {

    private final CreditApplicationRepo repository;
    private final KafkaTemmplate<String, CreditApplicationEvent> kafkaTemmplate;

    public Long createApplication(CreditApplicationRequest request) {
        CreditApplication creditApplication = new CreditApplication();
        BeanUtils.copyProperties(request, creditApplication);

        creditApplication = repository.save(creditApplication);

        CreditApplicationEvent creditApplicationEvent = new CreditApplicationEvent(
                creditApplication.getId(),
                creditApplication.getAmount(),
                creditApplication.getIncome()
        );

        kafkaTemmplate.send("credit", creditApplicationEvent);

        return creditApplication.getId();
    }

    public Status getStatus(Long id) {
        return repository.findById(id).map(CreditApplication::getStatus).orElseTrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
    }

    @RabbitListener(queues = "credit-decisions")

}
