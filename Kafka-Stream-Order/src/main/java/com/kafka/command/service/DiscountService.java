package com.kafka.command.service;

import com.kafka.api.request.DiscountRequest;
import com.kafka.command.action.DiscountAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiscountService {

    @Autowired
    private DiscountAction action;

    public void createDiscount(DiscountRequest request){
        action.publishToKafka(request);
    }
}
