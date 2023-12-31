package org.mateusz.numbergenerator;

import org.junit.jupiter.api.Test;
import org.mateusz.drawdate.DrawDateFacade;
import org.mateusz.numbergenerator.dto.WinningNumbersDto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class NumberGeneratorFacadeTest {

    private final DrawDateFacade drawDateFacade = mock(DrawDateFacade.class);
    private final WinningNumbersGenerator winningNumbersGenerator = mock(WinningNumbersGenerator.class, CALLS_REAL_METHODS);
    private final NumberGeneratorFacade numberGenerator = new NumberGeneratorFacade(
            drawDateFacade,
            winningNumbersGenerator,
            new WinningNumbersValidator(),
            new InMemoryWinningNumbersRepositoryTestImpl()
    );

    @Test
    void should_return_six_winning_numbers() {
        //given
        //when
        WinningNumbersDto winningNumbersDto = numberGenerator.generateWinningNumbers();
        //then
        assertThat(winningNumbersDto.winningNumbers().size()).isEqualTo(6);
    }

    @Test
    void should_return_six_distinct_winning_numbers() {
        //given
        WinningNumbersDto winningNumbers = numberGenerator.generateWinningNumbers();
        //when
        int winningNumbersAmount = new HashSet<>(winningNumbers.winningNumbers()).size();
        //then
        assertThat(winningNumbersAmount).isEqualTo(6);
    }

    @Test
    void should_return_six_winning_numbers_in_range_between_1_and_99() {
        //given
        int minNumber = 1;
        int maxNumber = 99;
        //when
        boolean areAllWinningNumbersInRange = numberGenerator.generateWinningNumbers()
                .winningNumbers()
                .stream()
                .allMatch(number -> number >= minNumber && number <= maxNumber);
        //then
        assertThat(areAllWinningNumbersInRange).isTrue();
    }

    @Test
    void should_throw_winning_numbers_not_found_exception_when_less_than_6_winning_numbers() {
        //given
        Collection<Integer> incorrectAmountOfWinningNumbers = Set.of(1, 2, 3, 4, 5);
        //when
        when(winningNumbersGenerator.generateSixRandomNumbers()).thenReturn(incorrectAmountOfWinningNumbers);
        //then
        assertThrows(WinningNumbersNotFoundException.class, numberGenerator::generateWinningNumbers, "Not enough winning numbers");
    }

    @Test
    void should_throw_winning_numbers_not_found_exception_when_at_least_1_winning_number_out_of_lower_range() {
        //given
        Collection<Integer> atLeastOneOfWinningNumbersOutOfRange = Set.of(0, 1, 2, 3, 4, 5);
        //when
        when(winningNumbersGenerator.generateSixRandomNumbers()).thenReturn(atLeastOneOfWinningNumbersOutOfRange);
        //then
        assertThrows(WinningNumbersNotFoundException.class, numberGenerator::generateWinningNumbers, "Winning numbers out of range!");
    }

    @Test
    void should_throw_winning_numbers_not_found_exception_when_at_least_1_winning_number_out_of_upper_range() {
        //given
        Collection<Integer> atLeastOneOfWinningNumbersOutOfRange = Set.of(100, 1, 2, 3, 4, 5);
        //when
        when(winningNumbersGenerator.generateSixRandomNumbers()).thenReturn(atLeastOneOfWinningNumbersOutOfRange);
        //then
        assertThrows(WinningNumbersNotFoundException.class, numberGenerator::generateWinningNumbers, "Winning numbers out of range!");
    }

}
