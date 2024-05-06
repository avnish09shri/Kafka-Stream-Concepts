package com.kafka.broker.stream.customer.preference;

import com.kafka.broker.message.CustomerPreferenceAggregateMessage;
import com.kafka.broker.message.CustomerPreferenceWishlistMessage;
import org.apache.kafka.streams.kstream.Aggregator;

public class CustomerPreferenceWishListAggregator implements Aggregator<String, CustomerPreferenceWishlistMessage, CustomerPreferenceAggregateMessage> {
    @Override
    public CustomerPreferenceAggregateMessage apply(String key, CustomerPreferenceWishlistMessage customerPreferenceWishlistMessage, CustomerPreferenceAggregateMessage customerPreferenceAggregateMessage) {
        customerPreferenceAggregateMessage.putWishlistItem(
                customerPreferenceWishlistMessage.getItemName(),
                customerPreferenceWishlistMessage.getWishlistDatetime());

        return customerPreferenceAggregateMessage;
    }
}
