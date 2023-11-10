package br.com.gubee.mensageria.consumer;

import br.com.gubee.mensageria.model.Order;
import br.com.gubee.mensageria.model.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @KafkaListener(topics = "order_created", groupId = "group_id", containerFactory = "orderKafkaListenerContainerFactory")
    public void consumeOrder(Order order) {
        // process the order, for now, just print it
        System.out.println(order);
    }

    @KafkaListener(topics = "order_status", groupId = "group_id", containerFactory = "orderEventKafkaListenerContainerFactory")
    public void consumeOrderEvent(OrderEvent event) {
        // process the order event
        System.out.println(event);
    }
}