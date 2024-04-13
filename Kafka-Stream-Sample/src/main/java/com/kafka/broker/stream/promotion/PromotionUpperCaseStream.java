package com.kafka.broker.stream.promotion;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class PromotionUpperCaseStream {

    // this class converts both key and value in uppercase.

    @Bean
    public KStream<String, String> kStreamPromotionUpperCase(StreamsBuilder builder){
        var sourceStream = builder.stream("t-commodity-promotion", Consumed.with(Serdes.String(), Serdes.String()));
        var upperCaseStream = sourceStream.mapValues(stream -> stream.toUpperCase());
        upperCaseStream.to("t-commodity-promotion-uppercase");

        sourceStream.print(Printed.<String,String>toSysOut().withLabel("Original Stream"));
        upperCaseStream.print(Printed.<String,String>toSysOut().withLabel("UpperCase Stream"));

        return sourceStream;
    }
}
