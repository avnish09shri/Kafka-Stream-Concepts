package com.kafka.command.action;

import com.kafka.api.request.InventoryRequest;
import com.kafka.broker.message.InventoryMessage;
import com.kafka.broker.producer.InventoryProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class InventoryAction {

	@Autowired
	private InventoryProducer producer;

	public void publishToKafka(InventoryRequest request, String type) {
		var message = new InventoryMessage();

		message.setLocation(request.getLocation());
		message.setItem(request.getItem());
		message.setQuantity(request.getQuantity());
		message.setType(type);
		message.setTransactionTime(request.getTransactionTime());

		producer.publish(message);
	}

}
