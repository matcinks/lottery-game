package org.mateusz.numbergenerator;

import java.util.Collection;

class WinningNumbersValidator {

    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 99;

    void validate(Collection<Integer> winningNumbers) {
        if (numbersOutOfRange(winningNumbers)) {
            throw new WinningNumbersNotFoundException("Winning numbers out of range!");
        }
        if (notEnoughNumbers(winningNumbers)) {
            throw new WinningNumbersNotFoundException("Not enough winning numbers!");
        }
    }

    private boolean numbersOutOfRange(Collection<Integer> winningNumbers) {
        return winningNumbers.stream()
                .distinct()
                .anyMatch(number -> number < MIN_NUMBER || number > MAX_NUMBER);
    }

    private boolean notEnoughNumbers(Collection<Integer> winningNumbers) {
        return winningNumbers.size() != 6;
    }
}