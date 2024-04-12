package com.kafka.broker.consumer;

import com.kafka.broker.message.OrderReplyMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderReplyConsumer {

    @KafkaListener(topics = "t-commodity-order-reply")
    public void listen(OrderReplyMessage message) {
        log.info("Reply message received : {}", message);
    }
}
