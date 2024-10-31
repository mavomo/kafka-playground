package com.playground.kafkaplayground.infra.config.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProperties {
    private String applicationName;
    private String bootstrapServers;

    @NestedConfigurationProperty
    private Producer producer = new Producer();

    @NestedConfigurationProperty
    private Consumer consumer = new Consumer();

    public String getAcks() {
        return this.producer.getAcks();
    }

    public Integer getRetries() {
        return this.producer.getRetries();
    }

    public Integer getBatchSize() {
        return this.producer.getBatchSize();
    }

    public Integer getLingerMemory() {
        return this.producer.getLingerMs();
    }

    public Integer getMemoryConfig() {
        return this.producer.getBufferMemory();
    }

    public static class Producer {
        private String keySerializer;
        private String valueSerializer;
        private String acks;
        private Integer retries;
        private Integer batchSize;
        private Integer lingerMs;
        private Integer bufferMemory;

        public String getKeySerializer() {
            return keySerializer;
        }

        public void setKeySerializer(String keySerializer) {
            this.keySerializer = keySerializer;
        }

        public String getValueSerializer() {
            return valueSerializer;
        }

        public void setValueSerializer(String valueSerializer) {
            this.valueSerializer = valueSerializer;
        }

        public String getAcks() {
            return acks;
        }

        public void setAcks(String acks) {
            this.acks = acks;
        }

        public Integer getRetries() {
            return retries;
        }

        public void setRetries(Integer retries) {
            this.retries = retries;
        }

        public Integer getBatchSize() {
            return batchSize;
        }

        public void setBatchSize(Integer batchSize) {
            this.batchSize = batchSize;
        }

        public Integer getLingerMs() {
            return lingerMs;
        }

        public void setLingerMs(Integer lingerMs) {
            this.lingerMs = lingerMs;
        }


        public Integer getBufferMemory() {
            return bufferMemory;
        }

        public void setBufferMemory(Integer bufferMemory) {
            this.bufferMemory = bufferMemory;
        }
    }

    public static class Consumer {
        private String keyDeserializer;
        private String valueDeserializer;
        private String autoOffsetReset;

        public String getKeyDeserializer() {
            return keyDeserializer;
        }

        public void setKeyDeserializer(String keyDeserializer) {
            this.keyDeserializer = keyDeserializer;
        }

        public String getValueDeserializer() {
            return valueDeserializer;
        }

        public void setValueDeserializer(String valueDeserializer) {
            this.valueDeserializer = valueDeserializer;
        }

        public String getAutoOffsetReset() {
            return autoOffsetReset;
        }

        public void setAutoOffsetReset(String autoOffsetReset) {
            this.autoOffsetReset = autoOffsetReset;
        }
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }
}
