package com.kafka.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPreferenceShoppingCartRequest {

	private String customerId;
	private String itemName;
	private int cartAmount;

}
