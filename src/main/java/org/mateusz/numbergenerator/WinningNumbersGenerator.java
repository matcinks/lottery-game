package org.mateusz.numbergenerator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

class WinningNumbersGenerator implements RandomNumberGenerator {

    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 99;

    @Override
    public Set<Integer> generateSixRandomNumbers() {
        Set<Integer> winningNumbers = new HashSet<>();
        while (isAmountOfNumbersInsufficient(winningNumbers)) {
            int randomNumber = ThreadLocalRandom.current().nextInt(MIN_NUMBER, MAX_NUMBER + 1);
            winningNumbers.add(randomNumber);
        }
        return winningNumbers;
    }

    private boolean isAmountOfNumbersInsufficient(Set<Integer> winningNumbers) {
        return winningNumbers.size() < 6;
    }
}
