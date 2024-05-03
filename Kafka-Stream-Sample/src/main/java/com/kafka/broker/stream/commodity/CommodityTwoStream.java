package com.kafka.broker.stream.commodity;

import com.kafka.broker.message.OrderMessage;
import com.kafka.broker.message.OrderPatternMessage;
import com.kafka.broker.message.OrderRewardMessage;
import com.kafka.util.CommodityStreamUtil;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

//@Configuration
public class CommodityTwoStream {

    @Bean
    public KStream<String, OrderMessage> kStreamCommodityTrading(StreamsBuilder builder){
        var stringSerde = Serdes.String();
        var orderSerde = new JsonSerde<>(OrderMessage.class);
        var orderPatternSerde = new JsonSerde<>(OrderPatternMessage.class);
        var orderRewardSerde = new JsonSerde<>(OrderRewardMessage.class);


        var maskedCreditCardStream = builder.stream(
                "t-commodity-order", Consumed.with(stringSerde, orderSerde))
                .mapValues(CommodityStreamUtil::maskCreditCard);

        // older alternative of branch because branch() is deprecated.
        /*var patternStreams = maskedCreditCardStream.mapValues(CommodityStreamUtil::mapToOrderPattern)
                .branch(CommodityStreamUtil.isPlastic() , (k, v) -> true);
        var plasticIndex = 0;
        var notPlasticIndex = 1;

        // plastic
        patternStreams[plasticIndex].to("t-commodity-pattern-two-plastic", Produced.with(stringSerde, orderPatternSerde));

        // not plastic
        patternStreams[notPlasticIndex].to("t-commodity-pattern-two-notplastic", Produced.with(stringSerde, orderPatternSerde));*/

        // newer alternative of branching
        maskedCreditCardStream.mapValues(CommodityStreamUtil::mapToOrderPattern)
                .split()
                .branch(CommodityStreamUtil.isPlastic(),
                        Branched.withConsumer(ks -> ks.to("t-commodity-pattern-two-plastic",
                                Produced.with(stringSerde, orderPatternSerde))))
                .branch((k, v) -> true,
                        Branched.withConsumer(ks -> ks.to("t-commodity-pattern-two-notplastic",
                                Produced.with(stringSerde, orderPatternSerde))));

        var rewardStream = maskedCreditCardStream
                .filter(CommodityStreamUtil.isLargeQuantity())
                .filterNot(CommodityStreamUtil.isCheap())
                .mapValues(CommodityStreamUtil::mapToOrderReward);

        rewardStream.to("t-commodity-reward-two", Produced.with(stringSerde, orderRewardSerde));

        var storageStream = maskedCreditCardStream.selectKey(CommodityStreamUtil.generateStorageKey());

        storageStream.to("t-commodity-storage-two", Produced.with(stringSerde, orderSerde));

        return maskedCreditCardStream;
    }
}
