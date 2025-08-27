package org.example.creditapplication.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.creditapplication.config.RabbitConfig;
import org.example.creditapplication.dto.CreditResult;
import org.example.creditapplication.model.Status;
import org.example.creditapplication.service.CreditApplicationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreditResultListener {
    
    private final CreditApplicationService creditApplicationService;
    
    @RabbitListener(queues = RabbitConfig.CREDIT_RESULT_QUEUE)
    public void handleCreditResult(CreditResult creditResult) {
        log.info("Received credit result: {}", creditResult);
        
        // Конвертируем строку в enum
        Status status = Status.valueOf(creditResult.getStatus());
        creditApplicationService.updateApplicationStatus(creditResult.getApplicationId(), status);
    }
}