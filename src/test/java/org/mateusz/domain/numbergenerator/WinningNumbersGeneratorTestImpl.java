package org.mateusz.domain.numbergenerator;

import java.util.Set;

public class WinningNumbersGeneratorTestImpl implements RandomNumberGenerator {

    private final Set<Integer> randomNumbers;

    public WinningNumbersGeneratorTestImpl() {
        randomNumbers = Set.of(1, 2, 3, 4, 5, 6);
    }

    public WinningNumbersGeneratorTestImpl(Set<Integer> numbers) {
        this.randomNumbers = numbers;
    }

    @Override
    public Set<Integer> generateSixRandomNumbers() {
        return randomNumbers;
    }
}
