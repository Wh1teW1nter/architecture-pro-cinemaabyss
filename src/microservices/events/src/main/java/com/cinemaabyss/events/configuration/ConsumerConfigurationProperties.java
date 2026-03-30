package com.cinemaabyss.events.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "kafka.consumer")
public class ConsumerConfigurationProperties {

    private String bootstrapServers;
    private String groupId;
}
