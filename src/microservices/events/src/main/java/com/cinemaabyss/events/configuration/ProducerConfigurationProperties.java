package com.cinemaabyss.events.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "kafka.producer")
public class ProducerConfigurationProperties {

    private String bootstrapServers;
}
