package com.kafka.broker.stream.flashsale;

import com.kafka.broker.message.FlashSaleVoteMessage;
import com.kafka.util.LocalDateTimeUtil;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.time.LocalDateTime;

public class FlashSaleVoteTwoValueTransformer implements ValueTransformer<FlashSaleVoteMessage, FlashSaleVoteMessage> {

    // to access processor API to add timestamp and its other uses.

    private final long voteStartTime;
    private final long voteEndTime;

    private ProcessorContext processorContext;

    public FlashSaleVoteTwoValueTransformer(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.voteStartTime = LocalDateTimeUtil.toEpochTimestamp(startDateTime);
        this.voteEndTime = LocalDateTimeUtil.toEpochTimestamp(endDateTime);
    }


    @Override
    public void init(ProcessorContext processorContext) {
        this.processorContext = processorContext;
    }

    @Override
    public FlashSaleVoteMessage transform(FlashSaleVoteMessage flashSaleVoteMessage) {
        var recordTime = processorContext.timestamp();
        return (recordTime >= voteStartTime && recordTime <=voteEndTime) ? flashSaleVoteMessage: null;
    }

    @Override
    public void close() {

    }
}
