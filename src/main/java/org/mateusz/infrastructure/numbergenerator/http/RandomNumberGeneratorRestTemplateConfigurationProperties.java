package org.mateusz.infrastructure.numbergenerator.http;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lotto.number-generator.http.client.config")
@Builder
public record RandomNumberGeneratorRestTemplateConfigurationProperties(
        long connectionTimeout,
        long readTimeout) {
}
