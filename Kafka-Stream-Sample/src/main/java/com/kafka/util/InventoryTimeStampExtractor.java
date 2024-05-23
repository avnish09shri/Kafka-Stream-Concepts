package com.kafka.util;

import com.kafka.broker.message.InventoryMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class InventoryTimeStampExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long l) {
        var inventoryMessage = (InventoryMessage) consumerRecord.value();
        return inventoryMessage != null ? LocalDateTimeUtil.toEpochTimestamp(inventoryMessage.getTransactionTime())
        : consumerRecord.timestamp();
    }
}
