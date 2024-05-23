package com.kafka.broker.stream.inventory;

import com.kafka.broker.message.InventoryMessage;
import com.kafka.util.InventoryTimeStampExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

//@Configuration
public class InventoryFourStream {

    // timestamp extractor

    @Bean
    public KStream<String, InventoryMessage> kStreamInventory(StreamsBuilder builder){
        var stringSerde = Serdes.String();
        var inventorySerde = new JsonSerde<>(InventoryMessage.class);
        var inventoryTimestampExtractor = new InventoryTimeStampExtractor();

        var inventoryStream = builder.stream("t-commodity-inventory",
                Consumed.with(stringSerde, inventorySerde, inventoryTimestampExtractor, null));

        inventoryStream.to("t-commodity-inventory-four", Produced.with(stringSerde, inventorySerde));

        return inventoryStream;

    }
}
