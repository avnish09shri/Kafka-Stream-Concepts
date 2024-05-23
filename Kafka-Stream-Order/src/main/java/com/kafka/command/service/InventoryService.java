package com.kafka.command.service;

import com.kafka.api.request.InventoryRequest;
import com.kafka.command.action.InventoryAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InventoryService {

	@Autowired
	private InventoryAction action;

	public void addInventory(InventoryRequest request) {
		action.publishToKafka(request, "ADD");
	}

	public void subtractInventory(InventoryRequest request) {
		action.publishToKafka(request, "REMOVE");
	}

}
