package com.kafka.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPurchaseMobileRequest {

	@Data
	public static class Location {
		private double latitude;

		private double longitude;

	}

	private int purchaseAmount;

	private String mobileAppVersion;

	private String operatingSystem;

	private Location location;

}
