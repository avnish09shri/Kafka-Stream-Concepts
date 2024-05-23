package com.kafka.broker.stream.inventory;

import com.kafka.broker.message.InventoryMessage;
import com.kafka.util.InventoryTimeStampExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;

//@Configuration
public class InventoryFiveStream {

	@SuppressWarnings("deprecation")
	@Bean
	public KStream<String, InventoryMessage> kStreamInventory(StreamsBuilder builder) {
		var stringSerde = Serdes.String();
		var inventorySerde = new JsonSerde<>(InventoryMessage.class);
		var inventoryTimestampExtractor = new InventoryTimeStampExtractor();
		var longSerde = Serdes.Long();

		var windowLength = Duration.ofHours(1l);
		var windowSerde = WindowedSerdes.timeWindowedSerdeFrom(String.class, windowLength.toMillis());

		var inventoryStream = builder.stream("t-commodity-inventory",
				Consumed.with(stringSerde, inventorySerde, inventoryTimestampExtractor, null));

		inventoryStream
				.mapValues((k, v) -> v.getType().equalsIgnoreCase("ADD") ? v.getQuantity() : (-1 * v.getQuantity()))
				.groupByKey().windowedBy(TimeWindows.ofSizeWithNoGrace(windowLength))
				.reduce(Long::sum, Materialized.with(stringSerde, longSerde)).toStream()
				.through("t-commodity-inventory-five", Produced.with(windowSerde, longSerde)).print(Printed.toSysOut());

		return inventoryStream;
	}

}
