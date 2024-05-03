package com.kafka.broker.stream.feedback;

import com.kafka.broker.message.FeedbackMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

//@Configuration
@Slf4j
public class FeedbackFiveStream {

    // first split then use through and then use its alternative.
    private static final Set<String> GOOD_WORDS = Set.of("happy", "good", "helpful","excellent");

    private static final Set<String> BAD_WORDS = Set.of("angry", "sad", "bad");


    @Bean
    public KStream<String, FeedbackMessage> kStreamFeedback(StreamsBuilder builder){
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedbackMessage.class);

        var sourceStream = builder.stream("t-commodity-feedback", Consumed.with(stringSerde, feedbackSerde));

        /*var feedBackStreams = sourceStream.flatMap(splitWords()).branch(isGoodWord(), isBadWord());

        feedBackStreams[0].through("t-commodity-feedback-five-good").groupByKey().count().toStream()
                .to("t-commodity-feedback-five-good-count");

        feedBackStreams[1].through("t-commodity-feedback-five-good").groupByKey().count().toStream()
                .to("t-commodity-feedback-five-bad-count");*/

        sourceStream.flatMap(splitWords()).split()
                .branch(isGoodWord(),
                        Branched.withConsumer(ks -> ks.repartition(Repartitioned.as("t-commodity-feedback-five-good"))
                                .groupByKey().count().toStream().to("t-commodity-feedback-five-good-count")))
                .branch(isBadWord(),
                        Branched.withConsumer(ks -> ks.repartition(Repartitioned.as("t-commodity-feedback-five-bad"))
                                .groupByKey().count().toStream().to("t-commodity-feedback-five-bad-count")));

        return sourceStream;

    }

    private Predicate<String, String> isBadWord() {
        return (key, value) -> BAD_WORDS.contains(value);
    }

    private Predicate<String, String> isGoodWord() {
        return (key, value) -> GOOD_WORDS.contains(value);
    }

    private KeyValueMapper<String, FeedbackMessage, Iterable<KeyValue<String, String>>> splitWords() {
        return (key, value) -> Arrays
                .asList(value.getFeedback().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+")).stream()
                .distinct().map(word -> KeyValue.pair(value.getLocation(), word)).collect(Collectors.toList());
    }

}
