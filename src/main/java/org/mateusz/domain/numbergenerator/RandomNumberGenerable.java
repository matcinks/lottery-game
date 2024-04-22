package org.mateusz.domain.numbergenerator;

import org.mateusz.domain.numbergenerator.dto.SixRandomNumbersDto;

import java.util.Set;

public interface RandomNumberGenerable {
    
    SixRandomNumbersDto generateSixRandomNumbers();
}
