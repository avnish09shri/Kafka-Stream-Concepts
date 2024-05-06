package com.kafka.command.service;

import com.kafka.api.request.CustomerPurchaseMobileRequest;
import com.kafka.api.request.CustomerPurchaseWebRequest;
import com.kafka.command.action.CustomerPurchaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerPurchaseService {

	@Autowired
	private CustomerPurchaseAction action;

	public String createPurchaseMobile(CustomerPurchaseMobileRequest request) {
		return action.publishMobileToKafka(request);
	}

	public String createPurchaseWeb(CustomerPurchaseWebRequest request) {
		return action.publishWebToKafka(request);
	}
}
