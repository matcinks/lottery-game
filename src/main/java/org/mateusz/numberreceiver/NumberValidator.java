package org.mateusz.numberreceiver;

import java.util.Collection;

class NumberValidator {

    private static final int MAX_NUMBERS_FROM_USER = 6;
    private static final int MAX_NUMBER_VALUE = 99;
    private static final int MIN_NUMBER_VALUE = 1;

    boolean areAllNumbersInRange(Collection<Integer> numbers) {
        return numbers.stream()
                .filter(number -> number >= MIN_NUMBER_VALUE)
                .filter(number -> number <= MAX_NUMBER_VALUE)
                .distinct()
                .count() == MAX_NUMBERS_FROM_USER;
    }

}
