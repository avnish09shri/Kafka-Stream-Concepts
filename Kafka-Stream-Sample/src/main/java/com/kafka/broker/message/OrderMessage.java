package com.kafka.broker.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage {

    private String orderLocation;
    private String orderNumber;
    private String creditCardNumber;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderDateTime;

    private String itemName;
    private int price;
    private int quantity;

    public OrderMessage copy() {

        var copy = new OrderMessage();
        copy.setCreditCardNumber(this.getCreditCardNumber());
        copy.setItemName(this.getItemName());
        copy.setOrderDateTime(this.getOrderDateTime());
        copy.setOrderLocation(this.getOrderLocation());
        copy.setOrderNumber(this.getOrderNumber());
        copy.setPrice(this.getPrice());
        copy.setQuantity(this.getQuantity());

        return copy;
    }
}
