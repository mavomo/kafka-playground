package com.playground.kafkaplayground.infra.config.kafka.producer;

import org.apache.kafka.clients.producer.Producer;

public interface ProducerFactoryListener <K,V>{

    default void producerAdded(String id, Producer<K, V> producer) {
    }

    default void producerRemoved(String id, Producer<K, V> producer) {
    }
}
