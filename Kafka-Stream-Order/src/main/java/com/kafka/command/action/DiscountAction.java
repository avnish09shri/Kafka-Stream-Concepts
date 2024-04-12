package com.kafka.command.action;

import com.kafka.api.request.DiscountRequest;
import com.kafka.broker.message.DiscountMessage;
import com.kafka.broker.producer.DiscountProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscountAction {

    @Autowired
    private DiscountProducer producer;
    public void publishToKafka(DiscountRequest request) {
        var message = new DiscountMessage(request.getDiscountCode(), request.getDiscountPercentage());
        producer.publish(message);
    }
}
