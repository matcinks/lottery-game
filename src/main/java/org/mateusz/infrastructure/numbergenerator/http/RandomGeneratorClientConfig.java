package org.mateusz.infrastructure.numbergenerator.http;

import org.mateusz.domain.numbergenerator.RandomNumberGenerable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RandomGeneratorClientConfig {

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(@Value("${lotto.number-generator.http.client.config.connection-timeout}") long connectionTimeout,
                                     @Value("${lotto.number-generator.http.client.config.read-timeout}") long readTimeout) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler())
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMinutes(readTimeout))
                .build();
    }

    @Bean
    public RandomNumberGenerable remoteNumberGeneratorClient(RestTemplate restTemplate,
                                                             @Value("${lotto.number-generator.http.client.config.uri}") String uri,
                                                             @Value("${lotto.number-generator.http.client.config.port}") int port) {
        return new RandomNumberGeneratorRestTemplate(restTemplate, uri, port);
    }
}
