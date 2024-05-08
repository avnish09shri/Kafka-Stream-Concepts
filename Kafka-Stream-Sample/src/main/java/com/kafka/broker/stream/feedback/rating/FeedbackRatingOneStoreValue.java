package com.kafka.broker.stream.feedback.rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRatingOneStoreValue {

    private long countRating;
    private long sumRating;
}
