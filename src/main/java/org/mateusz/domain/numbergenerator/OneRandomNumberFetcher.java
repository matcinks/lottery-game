package org.mateusz.domain.numbergenerator;

import org.mateusz.domain.numbergenerator.dto.OneRandomNumberResponseDto;

public interface OneRandomNumberFetcher {
    OneRandomNumberResponseDto retrieveOneRandomNumber(int lowerBand, int higherBand);
}
