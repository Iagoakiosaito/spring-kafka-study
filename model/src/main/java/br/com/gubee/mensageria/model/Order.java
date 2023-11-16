package br.com.gubee.mensageria.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Builder
public class Order {
    private String id;
    private List<String> products;
    private String deliveryAddress;
    private String paymentMethod;
}