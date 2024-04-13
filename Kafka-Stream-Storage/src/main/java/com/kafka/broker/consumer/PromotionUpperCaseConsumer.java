package com.kafka.broker.consumer;

import com.kafka.broker.message.PromotionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = "t-commodity-promotion-uppercase")
public class PromotionUpperCaseConsumer {

    @KafkaHandler
    public void listenPromotion(PromotionMessage message){
        log.info("Promotion UpperCase Message: {}", message);
    }
}
