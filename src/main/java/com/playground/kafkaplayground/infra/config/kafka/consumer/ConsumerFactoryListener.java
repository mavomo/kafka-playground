package com.playground.kafkaplayground.infra.config.kafka.consumer;


import org.apache.kafka.clients.consumer.Consumer;

public interface ConsumerFactoryListener<K,V> {
    default void consumerAdded(String id, Consumer<K, V> consumer) {}

    default void consumerRemoved(String id, Consumer<K, V> consumer) {}
}
