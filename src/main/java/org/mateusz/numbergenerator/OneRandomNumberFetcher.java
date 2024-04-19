package org.mateusz.numbergenerator;

import org.mateusz.numbergenerator.dto.OneRandomNumberResponseDto;

public interface OneRandomNumberFetcher {
    OneRandomNumberResponseDto retrieveOneRandomNumber(int lowerBand, int higherBand);
}
