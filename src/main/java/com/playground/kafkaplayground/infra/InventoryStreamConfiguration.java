package com.playground.kafkaplayground.infra;

import com.playground.kafkaplayground.domain.Inventory;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class InventoryStreamConfiguration {
    /*
    * We will need :
    * - a Store : persistent place to store the products
    * - consume the orders and create a new inventory : each order decrement the numbers of products
    * - Produce a new Inventory after each order in near real time
    * */


    @Bean
    public KStream<String, Inventory> inventoryKStream(StreamsBuilder streamsBuilder){

        return streamsBuilder.stream("");
    }
}
