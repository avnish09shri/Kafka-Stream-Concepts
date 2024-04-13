package com.kafka.broker.stream.promotion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.broker.message.PromotionMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class PromotionUpperCaseJsonStream {

    // this class converts only value in uppercase.

    private ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public KStream<String, String> kStreamPromotionUpperCase(StreamsBuilder builder){
        var stringSerde = Serdes.String();
        var sourceStream = builder.stream("t-commodity-promotion", Consumed.with(stringSerde, stringSerde));
        var upperCaseStream = sourceStream.mapValues(this::upperCasePromotionCode);

        upperCaseStream.to("t-commodity-promotion-uppercase");
        sourceStream.print(Printed.<String, String>toSysOut().withLabel("Json Original Stream"));
        upperCaseStream.print(Printed.<String, String>toSysOut().withLabel("Json Original Stream"));

        return sourceStream;
    }

    public String upperCasePromotionCode(String message) {
        PromotionMessage original = null;
        try {
            original = objectMapper.readValue(message, PromotionMessage.class);
            var converted = new PromotionMessage(original.getPromotionCode().toUpperCase());
            return objectMapper.writeValueAsString(converted);
        } catch (Exception e) {
            return "";
        }
    }
}
