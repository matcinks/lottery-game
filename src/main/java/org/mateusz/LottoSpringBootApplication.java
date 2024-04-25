package org.mateusz;

import org.mateusz.domain.numbergenerator.WinningNumbersGeneratorFacadeConfigurationProperties;
import org.mateusz.infrastructure.numbergenerator.http.RandomNumberGeneratorRestTemplateConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({WinningNumbersGeneratorFacadeConfigurationProperties.class, RandomNumberGeneratorRestTemplateConfigurationProperties.class})
@EnableScheduling
public class LottoSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(LottoSpringBootApplication.class, args);
    }
}