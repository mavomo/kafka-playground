package com.playground.kafkaplayground;

import com.playground.kafkaplayground.infra.config.kafka.KafkaProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

public class KafkaPropertiesForTests {//TODO: I doubt it works without an @Configuration config
    private String bootstrapServers;
    @Value("${spring.kafka.sasl.mechanism}")
    private String saslMechanism;
    @Value("${spring.kafka.sasl.jaas.username}")
    private String jaasUsername;
    @Value("${spring.kafka.sasl.jaas.password}")
    private String jaasPassword;
    @Value("${spring.kafka.security.protocol}")
    private String securityProtocol;
    @Value("${spring.kafka.session.timeout.ms}")
    private int sessionTimeoutMs;

    @NestedConfigurationProperty
    private KafkaProperties.Producer producer = new KafkaProperties.Producer();

    @NestedConfigurationProperty
    private KafkaProperties.Consumer consumer = new KafkaProperties.Consumer();

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public String getSaslMechanism() {
        return saslMechanism;
    }

    public String getJaasUsername() {
        return jaasUsername;
    }

    public String getJaasPassword() {
        return jaasPassword;
    }

    public String getSecurityProtocol() {
        return securityProtocol;
    }

    public int getSessionTimeoutMs() {
        return sessionTimeoutMs;
    }

    public KafkaProperties.Producer getProducer() {
        return producer;
    }

    public KafkaProperties.Consumer getConsumer() {
        return consumer;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public void setSaslMechanism(String saslMechanism) {
        this.saslMechanism = saslMechanism;
    }

    public void setJaasUsername(String jaasUsername) {
        this.jaasUsername = jaasUsername;
    }

    public void setJaasPassword(String jaasPassword) {
        this.jaasPassword = jaasPassword;
    }

    public void setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
    }

    public void setSessionTimeoutMs(int sessionTimeoutMs) {
        this.sessionTimeoutMs = sessionTimeoutMs;
    }

    public void setProducer(KafkaProperties.Producer producer) {
        this.producer = producer;
    }

    public void setConsumer(KafkaProperties.Consumer consumer) {
        this.consumer = consumer;
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
}
