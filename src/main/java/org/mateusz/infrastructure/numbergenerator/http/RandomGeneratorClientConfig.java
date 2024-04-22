package org.mateusz.infrastructure.numbergenerator.http;

import org.mateusz.domain.numbergenerator.RandomNumberGenerable;
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
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler())
                .setConnectTimeout(Duration.ofMillis(5000))
                .setReadTimeout(Duration.ofMinutes(5000))
                .build();
    }

    @Bean
    public RandomNumberGenerable remoteNumberGeneratorClient(RestTemplate restTemplate) {
        return new RandomNumberGeneratorRestTemplate(restTemplate);
    }
}
