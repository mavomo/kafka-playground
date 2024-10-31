package com.playground.kafkaplayground.infra.config.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {
    private String applicationName;
    private String bootstrapServers;
    private String saslMechanism;
    private String jaasUsername;
    private String jaasPassword;
    private String securityProtocol;
    private int sessionTimeoutMs;

    @NestedConfigurationProperty
    private Producer producer = new Producer();

    @NestedConfigurationProperty
    private Consumer consumer = new Consumer();

    public String getApplicationName() {
        return applicationName;
    }

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

    public Producer getProducer() {
        return producer;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
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

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public void setConsumer(Consumer consumer) {
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
