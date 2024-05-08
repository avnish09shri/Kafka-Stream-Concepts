package com.kafka.broker.stream.feedback.rating;

import com.kafka.broker.message.FeedbackMessage;
import com.kafka.broker.message.FeedbackRatingOneMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Optional;

public class FeedbackRatingOneValueTransformer implements ValueTransformer<FeedbackMessage, FeedbackRatingOneMessage> {

    private ProcessorContext processorContext;

    private final String stateStoreName;

    private KeyValueStore<String, FeedbackRatingOneStoreValue> ratingStateStore;

    public FeedbackRatingOneValueTransformer(String stateStoreName) {
        if (StringUtils.isEmpty(stateStoreName)) {
            throw new IllegalArgumentException("stateStoreName must not empty");
        }

        this.stateStoreName = stateStoreName;
    }

    @Override
    public void init(ProcessorContext processorContext) {
        this.processorContext = processorContext;
        this.ratingStateStore =  this.processorContext.getStateStore(stateStoreName);
    }

    @Override
    public FeedbackRatingOneMessage transform(FeedbackMessage value) {
        var storeValue = Optional.ofNullable(ratingStateStore.get(value.getLocation()))
                .orElse(new FeedbackRatingOneStoreValue());

        var newSumRating = storeValue.getSumRating() + value.getRating();
        storeValue.setSumRating(newSumRating);

        var newCountRating = storeValue.getCountRating() + 1;
        storeValue.setCountRating(newCountRating);

        ratingStateStore.put(value.getLocation(), storeValue);


        var branchRating = new FeedbackRatingOneMessage();
        branchRating.setLocation(value.getLocation());

        double averageRating = Math.round((double) newSumRating / newCountRating * 10d) / 10d;
        branchRating.setAverageRating(averageRating);

        return branchRating;

    }

    @Override
    public void close() {

    }
}
