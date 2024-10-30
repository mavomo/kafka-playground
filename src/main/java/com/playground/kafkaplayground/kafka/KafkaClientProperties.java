package com.playground.kafkaplayground.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka.client")
public class KafkaClientProperties {

    private String applicationName;

    // Connection configuration
    private String bootstrapUrl;
    private String securityProtocol;

    // SASL
    private boolean activateSasl;
    private String mechanism;
    private String jaasUsername;
    private String jaasPassword;

    // Common admin client properties
    private boolean valueStringDeserializer = false;
    private boolean enableAutoCommitConfig = false;
    private String autoOffsetResetConfig = "earliest";

    // Producer performance
    private int producerBatchSizeConfig = 16384;
    private int producerLingerMsConfig = 0;

    // Consumer performance
    private int fetchMinBytesConfig = 1;
    private int fetchMaxWaitMsConfig = 500;
    private int maxPollRecordsConfig = 500;
}
