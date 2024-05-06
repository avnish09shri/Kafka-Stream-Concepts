package com.kafka.broker.stream.customer.preference;

import com.kafka.broker.message.CustomerPreferenceAggregateMessage;
import com.kafka.broker.message.CustomerPreferenceShoppingCartMessage;
import org.apache.kafka.streams.kstream.Aggregator;

public class CustomerPreferenceShoppingCartAggregator implements Aggregator<String, CustomerPreferenceShoppingCartMessage, CustomerPreferenceAggregateMessage> {
    @Override
    public CustomerPreferenceAggregateMessage apply(String key, CustomerPreferenceShoppingCartMessage customerPreferenceShoppingCartMessage, CustomerPreferenceAggregateMessage customerPreferenceAggregateMessage) {
        customerPreferenceAggregateMessage.putShoppingCartItem(
                customerPreferenceShoppingCartMessage.getItemName(),
                customerPreferenceShoppingCartMessage.getCartDatetime());
        return customerPreferenceAggregateMessage;
    }
}
