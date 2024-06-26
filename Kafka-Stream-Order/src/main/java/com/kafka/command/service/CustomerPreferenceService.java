package com.kafka.command.service;

import com.kafka.api.request.CustomerPreferenceShoppingCartRequest;
import com.kafka.api.request.CustomerPreferenceWishlistRequest;
import com.kafka.command.action.CustomerPreferenceAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerPreferenceService {

	@Autowired
	private CustomerPreferenceAction action;

	public void createShoppingCart(CustomerPreferenceShoppingCartRequest request) {
		action.publishShoppingCart(request);
	}

	public void createWishlist(CustomerPreferenceWishlistRequest request) {
		action.publishWishlist(request);
	}
}
