package gubee.msg.mensageria;

import gubee.msg.mensageria.streams.Producer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
class KafkaController {

    private Producer producer;

    @RequestMapping("/send/{name}")
    public String send(@PathVariable String name) {
        producer.sendMessage("Hello" + name + "!");
        return "OK";
    }
}