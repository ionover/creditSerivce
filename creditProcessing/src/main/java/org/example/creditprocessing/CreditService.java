package org.example.creditprocessing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.creditprocessing.dto.CreditApplicationEvent;
import org.example.creditprocessing.dto.CreditResult;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditService {

    private final RabbitTemplate rabbitTemplate;

    @KafkaListener(topics = "credit-applications", groupId = "credit-processing-group")
    public void processMessage(CreditApplicationEvent creditApplicationEvent) {
        log.info("Processing credit application: {}", creditApplicationEvent);

        // Простая логика: одобряем если сумма кредита меньше 50% от дохода
        boolean approval = creditApplicationEvent.getAmount() < creditApplicationEvent.getIncome() * 0.5;
        
        String status = approval ? "SUCCESS" : "FAILED";
        
        CreditResult creditResult = new CreditResult(
            creditApplicationEvent.getApplicationId(), 
            status
        );
        
        rabbitTemplate.convertAndSend("credit.result.queue", creditResult);
        log.info("Sent credit result: {}", creditResult);
    }
}
