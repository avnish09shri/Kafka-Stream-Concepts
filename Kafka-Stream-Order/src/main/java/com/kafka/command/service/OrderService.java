package com.kafka.command.service;

import com.kafka.api.request.OrderRequest;
import com.kafka.command.action.OrderAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private OrderAction orderAction;

    public String saveOrder(OrderRequest request) {
        var order = orderAction.convertToOrder(request);
        orderAction.saveToDatabase(order);

        // flatten message & publish
        order.getItems().forEach(orderAction::publishToKafka);

        return order.getOrderNumber();
    }
}
