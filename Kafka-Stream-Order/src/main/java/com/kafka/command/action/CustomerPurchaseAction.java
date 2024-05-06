package com.kafka.command.action;

import com.kafka.api.request.CustomerPurchaseMobileRequest;
import com.kafka.api.request.CustomerPurchaseWebRequest;
import com.kafka.broker.message.CustomerPurchaseMobileMessage;
import com.kafka.broker.message.CustomerPurchaseWebMessage;
import com.kafka.broker.producer.CustomerPurchaseProducer;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerPurchaseAction {

	@Autowired
	private CustomerPurchaseProducer producer;

	public String publishMobileToKafka(CustomerPurchaseMobileRequest request) {
		var purchaseNumber = "CP-MOBILE-" + RandomStringUtils.randomAlphanumeric(6).toUpperCase();
		var location = new CustomerPurchaseMobileMessage.Location(request.getLocation().getLatitude(),
				request.getLocation().getLongitude());

		var message = new CustomerPurchaseMobileMessage(purchaseNumber, request.getPurchaseAmount(),
				request.getMobileAppVersion(), request.getOperatingSystem(), location);

		producer.publishPurchaseMobile(message);

		return purchaseNumber;
	}

	public String publishWebToKafka(CustomerPurchaseWebRequest request) {
		var purchaseNumber = "CP-WEB-" + RandomStringUtils.randomAlphanumeric(6).toUpperCase();

		var message = new CustomerPurchaseWebMessage(purchaseNumber, request.getPurchaseAmount(), request.getBrowser(),
				request.getOperatingSystem());

		producer.publishPurchaseWeb(message);

		return purchaseNumber;
	}

}
