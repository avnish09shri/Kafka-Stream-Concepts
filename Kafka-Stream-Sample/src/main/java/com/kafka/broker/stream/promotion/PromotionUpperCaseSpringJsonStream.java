package com.kafka.broker.stream.promotion;

import com.kafka.broker.message.PromotionMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class PromotionUpperCaseSpringJsonStream {

    // this class is using spring json serde for uppercase.

    @Bean
    public KStream<String, PromotionMessage> kStreamPromotionUpperCase(StreamsBuilder builder){
        var stringSerde = Serdes.String();
        var jsonSerde = new JsonSerde<>(PromotionMessage.class);

        var sourceStream = builder.stream("t-commodity-promotion", Consumed.with(stringSerde, jsonSerde));
        var upperCaseStream = sourceStream.mapValues(this::upperCasePromotionCode);

        upperCaseStream.to("t-commodity-promotion-uppercase", Produced.with(stringSerde, jsonSerde));
        sourceStream.print(Printed.<String, PromotionMessage>toSysOut().withLabel("Json Serde Original Stream"));
        upperCaseStream.print(Printed.<String, PromotionMessage>toSysOut().withLabel("Json Serde Uppercase Stream"));

        return sourceStream;
    }

    public PromotionMessage upperCasePromotionCode(PromotionMessage message) {
        return new PromotionMessage(message.getPromotionCode().toUpperCase());
    }
}
