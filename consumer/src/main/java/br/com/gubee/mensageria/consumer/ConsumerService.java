package br.com.gubee.mensageria.consumer;
import br.com.gubee.mensageria.model.OrderEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConsumerService {

    Map<String, Set<Object>> consumedRecords = new ConcurrentHashMap<>();

    @KafkaListener(topicPartitions = @TopicPartition(topic = "order_created", partitions = {"0"}), groupId = "group_id", containerFactory = "orderKafkaListenerContainerFactory")
    public void consumeOrderPartition0(ConsumerRecord<?, ?> consumerRecord) {
        trackConsumedPartitions("consumer-1", consumerRecord.partition());
        System.out.println("Consumer-0");
        System.out.println(consumerRecord.partition());
        System.out.println("Thread ID: " + Thread.currentThread().getId());
        System.out.println("-----------------------");

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "order_created", partitions = {"1"}), groupId = "group_id", containerFactory = "orderKafkaListenerContainerFactory")
    public void consumeOrderPartition1(ConsumerRecord<?, ?> consumerRecord) {
        trackConsumedPartitions("consumer-2", consumerRecord.partition());
        System.out.println("Consumer-1");
        System.out.println(consumerRecord.partition());
        System.out.println("Thread ID: " + Thread.currentThread().getId());
        System.out.println("-----------------------");

    }

    @KafkaListener(topics = "order_created", groupId = "group_id", containerFactory = "orderKafkaListenerContainerFactory")
    public void consumeOrderAllPartitions(ConsumerRecord<?, ?> consumerRecord) {
        trackConsumedPartitions("consumer-3", consumerRecord.partition());
        System.out.println("Consumer-3");
        System.out.println(consumerRecord.partition());
        System.out.println("Thread ID: " + Thread.currentThread().getId());
        System.out.println("-----------------------");
    }

    @KafkaListener(topics = "order_status", groupId = "group_id", containerFactory = "orderEventKafkaListenerContainerFactory")
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