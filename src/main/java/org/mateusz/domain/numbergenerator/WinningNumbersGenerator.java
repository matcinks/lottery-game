package org.mateusz.domain.numbergenerator;

import lombok.AllArgsConstructor;
import org.mateusz.domain.numbergenerator.dto.OneRandomNumberResponseDto;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
class WinningNumbersGenerator implements RandomNumberGenerator {

    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 99;

    private final OneRandomNumberFetcher client;

    @Override
    public Set<Integer> generateSixRandomNumbers() {
        Set<Integer> winningNumbers = new HashSet<>();
        while (isAmountOfNumbersInsufficient(winningNumbers)) {
            OneRandomNumberResponseDto randomNumberResponseDto = client.retrieveOneRandomNumber(MIN_NUMBER, MAX_NUMBER);
            int randomNumber = randomNumberResponseDto.number();
            winningNumbers.add(randomNumber);
        }
        return winningNumbers;
    }

    private boolean isAmountOfNumbersInsufficient(Set<Integer> winningNumbers) {
        return winningNumbers.size() < 6;
    }
}
