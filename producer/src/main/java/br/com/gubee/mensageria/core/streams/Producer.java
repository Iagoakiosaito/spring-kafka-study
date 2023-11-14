package br.com.gubee.mensageria.core.streams;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import br.com.gubee.mensageria.model.*;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class Producer {

    @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private int id;

    public void sendOrder(Order order) {
        id += 1;
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("order_created", String.valueOf(id), order);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + id +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        id + "] due to : " + ex.getMessage());
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