package org.mateusz.domain.numbergenerator;

import org.mateusz.domain.drawdate.DrawDateFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WinningNumbersGeneratorConfiguration {

    @Bean
    WinningNumbersRepository winningNumbersRepository() {
        return new WinningNumbersRepository() {
            @Override
            public WinningNumbers save(WinningNumbers winningNumbers) {
                return null;
            }
        };
    }

    @Bean
    WinningNumbersGeneratorFacade winningNumbersGeneratorFacade(DrawDateFacade drawDateFacade, RandomNumberGenerable winningNumbersGenerator, WinningNumbersRepository winningNumbersRepository, WinningNumbersGeneratorFacadeConfigurationProperties properties) {
        WinningNumbersValidator winningNumbersValidator = new WinningNumbersValidator();
        return new WinningNumbersGeneratorFacade(drawDateFacade, winningNumbersGenerator, winningNumbersValidator, winningNumbersRepository, properties);
    }

    WinningNumbersGeneratorFacade createForTest(DrawDateFacade drawDateFacade,
                                                RandomNumberGenerable winningNumbersGenerator,
                                                WinningNumbersRepository winningNumbersRepository) {
        WinningNumbersGeneratorFacadeConfigurationProperties properties = WinningNumbersGeneratorFacadeConfigurationProperties.builder()
                .lowerBand(1)
                .upperBand(99)
                .count(6)
                .build();
        return winningNumbersGeneratorFacade(drawDateFacade, winningNumbersGenerator, winningNumbersRepository, properties);
//        WinningNumbersValidator winningNumbersValidator = new WinningNumbersValidator();
//        return new WinningNumbersGeneratorFacade(drawDateFacade, winningNumbersGenerator, winningNumbersValidator, winningNumbersRepository, properties);
    }
}
