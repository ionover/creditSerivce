package org.example.creditprocessing;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final RabbitTemplate rabbitTemplate;

    @KafkaListener(topic = "credit-applications", groupId = "credit-processing-service")
    public void processMessage(CreditApplicationEvent creditApplicationEvent) {

        boolean approval = creditApplicationEvent.getAmount < creditApplicationEvent.getIncome * 0.5;

        CerditDecisionEvent  decisionEvent = new CerditDecisionEvent(creditApplicationEvent.getApplicationID(), approval);
        rabbitTemplate.convertAndSend("credit-decisions", decisionEvent)
    }
}
