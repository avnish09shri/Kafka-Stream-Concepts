package com.kafka.broker.serde;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class CustomJsonSerde<T> implements Serde<T> {

    private CustomJsonSerializer<T> serializer;

    private CustomerJsonDeSerializer<T> deSerializer;

    public CustomJsonSerde(CustomJsonSerializer<T> serializer, CustomerJsonDeSerializer<T> deSerializer) {
        this.serializer = serializer;
        this.deSerializer = deSerializer;
    }

    @Override
    public Serializer<T> serializer() {
        return serializer;
    }

    @Override
    public Deserializer<T> deserializer() {
        return deSerializer;
    }
}
