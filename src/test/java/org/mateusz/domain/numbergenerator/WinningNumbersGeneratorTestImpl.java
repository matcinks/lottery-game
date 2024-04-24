package org.mateusz.domain.numbergenerator;

import org.mateusz.domain.numbergenerator.dto.SixRandomNumbersDto;

import java.util.Set;

public class WinningNumbersGeneratorTestImpl implements RandomNumberGenerable {

    private final Set<Integer> generatedNumbers;

    public WinningNumbersGeneratorTestImpl() {
        generatedNumbers = Set.of(1, 2, 3, 4, 5, 6);
    }

    public WinningNumbersGeneratorTestImpl(Set<Integer> numbers) {
        this.generatedNumbers = numbers;
    }

    @Override
    public SixRandomNumbersDto generateSixRandomNumbers(int lowerBand, int upperBand, int count) {
        return SixRandomNumbersDto.builder()
                .numbers(generatedNumbers)
                .build();
    }
}