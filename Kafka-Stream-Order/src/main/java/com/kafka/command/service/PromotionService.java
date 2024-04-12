package com.kafka.command.service;

import com.kafka.api.request.PromotionRequest;
import com.kafka.command.action.PromotionAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromotionService {

    @Autowired
    private PromotionAction action;

    public void createPromotion(PromotionRequest request){
        action.publishToKafka(request);
    }
}
