package br.com.gubee.mensageria.consumer;

import br.com.gubee.mensageria.model.Order;
import br.com.gubee.mensageria.model.OrderEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConsumerService {

    Map<String, Set<Object>> consumedRecords = new ConcurrentHashMap<>();

    @KafkaListener(topics = "order_created", groupId = "group_id-3", containerFactory = "orderKafkaListenerContainerFactory", concurrency = "3")
    public void consumeOrder(ConsumerRecord<?, ?> consumerRecord) {
        trackConsumedPartitions("consumer-1", consumerRecord.partition());
        System.out.println(consumerRecord.partition());
        System.out.println("Thread ID: " + Thread.currentThread().getId());
    }

    @KafkaListener(topics = "order_status", groupId = "group_id-3", containerFactory = "orderEventKafkaListenerContainerFactory", concurrency = "3")
    public void consumeOrderEvent(OrderEvent event) {
        System.out.println(event);
        System.out.println("Thread ID: " + Thread.currentThread().getId());
    }

    private void trackConsumedPartitions(String consumerName, int partitionNumber) {
        consumedRecords.computeIfAbsent(consumerName, k -> new HashSet<>());
        consumedRecords.computeIfPresent(consumerName, (k, v) -> {
            v.add(String.valueOf(partitionNumber));
            return v;
        });
    }
}