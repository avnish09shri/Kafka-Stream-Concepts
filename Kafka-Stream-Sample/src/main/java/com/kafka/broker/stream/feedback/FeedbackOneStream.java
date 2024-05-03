package com.kafka.broker.stream.feedback;

import com.kafka.broker.message.FeedbackMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

//@Configuration
@Slf4j
public class FeedbackOneStream {

    // no branch location as key.

    private static final Set<String> GOOD_WORDS = Set.of("happy", "good", "helpful");

    @Bean
    public KStream<String, String> kStreamFeedback(StreamsBuilder builder){
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedbackMessage.class);

        var goodFeedbackStream = builder.stream("t-commodity-feedback", Consumed.with(stringSerde, feedbackSerde))
                .flatMapValues(mapperGoodValues());

        goodFeedbackStream.to("t-commodity-feedback-one-good");

        return goodFeedbackStream;

    }

    private ValueMapper<FeedbackMessage, Iterable<String>> mapperGoodValues() {
        return feedbackMessage -> Arrays.asList(feedbackMessage.getFeedback()
                .replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+"))
                .stream().filter(word -> GOOD_WORDS.contains(word)).distinct().collect(Collectors.toList());
    }
}
