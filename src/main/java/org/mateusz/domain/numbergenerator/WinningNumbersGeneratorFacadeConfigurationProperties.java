package org.mateusz.domain.numbergenerator;


import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lotto.number-generator.facade")
@Builder
public record WinningNumbersGeneratorFacadeConfigurationProperties(
        int lowerBand,
        int upperBand,
        int count) {
}
