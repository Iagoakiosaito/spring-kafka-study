package gubee.msg.mensageria.streams;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @KafkaListener(topics = "gubee", groupId = "group-1")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
