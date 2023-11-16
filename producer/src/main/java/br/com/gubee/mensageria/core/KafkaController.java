package br.com.gubee.mensageria.core;

import br.com.gubee.mensageria.core.streams.Producer;
import br.com.gubee.mensageria.model.Order;
import br.com.gubee.mensageria.model.OrderEvent;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
class KafkaController {

    private Producer producer;

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String createOrder(@RequestBody Order order) {
        producer.sendOrder(order);
        return "OK";
    }

    @RequestMapping(value = "/order/{id}/status", method = RequestMethod.PUT)
    public String updateOrderStatus(@PathVariable String id, @RequestBody OrderEvent event) {
        producer.sendOrderEvent(event);
        return "OK";
    }
}