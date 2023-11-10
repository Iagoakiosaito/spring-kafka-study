package br.com.gubee.mensageria.core.streams;

import org.springframework.kafka.core.KafkaTemplate;
import br.com.gubee.mensageria.model.*;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class Producer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public Producer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(Order order) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("order_created", order.getId(), order);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + order.getId() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        order.getId() + "] due to : " + ex.getMessage());
            }
        });
    }

    public void sendOrderEvent(OrderEvent event) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(event.getEventType(), event.getOrderId(), event);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + event.getOrderId() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        event.getOrderId() + "] due to : " + ex.getMessage());
            }
        });
    }
}