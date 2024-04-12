package com.kafka.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "order")
public class Order {

	@Id
	private String orderId;

	private String orderNumber;
	private String orderLocation;
	private LocalDateTime orderDateTime;
	private String creditCardNumber;
	@DocumentReference
	private List<OrderItem> items;

}
