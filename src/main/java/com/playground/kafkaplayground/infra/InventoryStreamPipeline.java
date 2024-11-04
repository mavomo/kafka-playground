package com.playground.kafkaplayground.infra;

import com.playground.kafkaplayground.domain.Inventory;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InventoryStreamPipeline {
    /*
     * We will need :
     * - a Store : persistent place to store the products
     * - consume the orders and create a new inventory : each order decrement the numbers of products
     * - Produce a new Inventory after each order in near real time
     * */
    private static final Serde<String> STRING_SERDE = Serdes.String();
    private final ProductService productService;

    public InventoryStreamPipeline(ProductService productService) {
        this.productService = productService;
    }


    @Autowired
    public void buildInventoryStream(StreamsBuilder streamsBuilder) {
        //On initialise un consumer à la manière KStream
        KStream<String, OrderTreated> orderStream = streamsBuilder
                .stream("dev.playground.order.treated",
                        Consumed.with(STRING_SERDE, new JsonSerde<>(OrderTreated.class))
                );
        orderStream.mapValues(order -> order.items())
                .flatMapValues(items ->
                        items.stream().map(item -> {
                            long initialQuantity = productService.getProductQuantitiesById(item.getProductId());
                            Inventory itemInventory = new Inventory(LocalDateTime.now(), item.getProductId(), initialQuantity);
                            return itemInventory;
                        }).toList()
                ).to("dev.playground.inventory.created");

    }
}
