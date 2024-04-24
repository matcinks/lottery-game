package org.mateusz.domain.numbergenerator;

import org.mateusz.domain.numbergenerator.dto.SixRandomNumbersDto;

public interface RandomNumberGenerable {
    
    SixRandomNumbersDto generateSixRandomNumbers(int lowerBand, int upperBand, int count);
}
