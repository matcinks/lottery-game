package org.mateusz.domain.numbergenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mateusz.domain.drawdate.DrawDateFacade;
import org.mateusz.domain.drawdate.dto.DrawDateDto;
import org.mateusz.domain.numbergenerator.dto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class WinningNumbersGeneratorFacadeTest {
    private static final DrawDateDto DUMMY_DRAW_DATE_DTO = DrawDateDto.builder()
            .id("001")
            .time(LocalDateTime.now())
            .build();
    private final DrawDateFacade drawDateFacade = mock(DrawDateFacade.class);
    private final WinningNumbersRepository winningNumbersTestRepository = new InMemoryWinningNumbersRepositoryTestImpl();

    @BeforeEach
    public void setUp() {
        when(drawDateFacade.getNextDrawDate()).thenReturn(DUMMY_DRAW_DATE_DTO);
    }

    @Test
    void should_return_six_winning_numbers() {
        //given
        RandomNumberGenerable winningNumbersGenerator = new WinningNumbersGeneratorTestImpl();
        WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WinningNumbersGeneratorConfiguration().createForTest(
                drawDateFacade,
                winningNumbersGenerator,
                winningNumbersTestRepository);
        //when
        WinningNumbersDto winningNumbersDto = winningNumbersGeneratorFacade.generateWinningNumbers();
        //then
        assertThat(winningNumbersDto.winningNumbers().size()).isEqualTo(6);
    }

    @Test
    void should_return_six_distinct_winning_numbers() {
        //given
        RandomNumberGenerable winningNumbersGenerator = new WinningNumbersGeneratorTestImpl();
        WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WinningNumbersGeneratorConfiguration().createForTest(
                drawDateFacade,
                winningNumbersGenerator,
                winningNumbersTestRepository);
        WinningNumbersDto winningNumbers = winningNumbersGeneratorFacade.generateWinningNumbers();
        //when
        int winningNumbersAmount = new HashSet<>(winningNumbers.winningNumbers()).size();
        //then
        assertThat(winningNumbersAmount).isEqualTo(6);
    }

    @Test
    void should_return_six_winning_numbers_in_range_between_1_and_99() {
        //given
        RandomNumberGenerable winningNumbersGenerator = new WinningNumbersGeneratorTestImpl();
        WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WinningNumbersGeneratorConfiguration().createForTest(
                drawDateFacade,
                winningNumbersGenerator,
                winningNumbersTestRepository);
        int minNumber = 1;
        int maxNumber = 99;
        //when
        boolean areAllWinningNumbersInRange = winningNumbersGeneratorFacade.generateWinningNumbers()
                .winningNumbers()
                .stream()
                .allMatch(number -> number >= minNumber && number <= maxNumber);
        //then
        assertThat(areAllWinningNumbersInRange).isTrue();
    }

    @Test
    void should_throw_winning_numbers_not_found_exception_when_less_than_6_winning_numbers() {
        //given
        RandomNumberGenerable winningNumbersGenerator = new WinningNumbersGeneratorTestImpl(Set.of(1, 2, 3, 4, 5));
        WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WinningNumbersGeneratorConfiguration().createForTest(
                drawDateFacade,
                winningNumbersGenerator,
                winningNumbersTestRepository);
        //when
        //then
        assertThrows(WinningNumbersNotFoundException.class, winningNumbersGeneratorFacade::generateWinningNumbers, "Not enough winning numbers");
    }

    @Test
    void should_throw_winning_numbers_not_found_exception_when_at_least_1_winning_number_out_of_lower_range() {
        //given
        RandomNumberGenerable winningNumbersGenerator = new WinningNumbersGeneratorTestImpl(Set.of(0, 2, 3, 4, 5, 6));
        WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WinningNumbersGeneratorConfiguration().createForTest(
                drawDateFacade,
                winningNumbersGenerator,
                winningNumbersTestRepository);
        //when
        //then
        assertThrows(WinningNumbersNotFoundException.class, winningNumbersGeneratorFacade::generateWinningNumbers, "Winning numbers out of range!");
    }

    @Test
    void should_throw_winning_numbers_not_found_exception_when_at_least_1_winning_number_out_of_upper_range() {
        //given
        RandomNumberGenerable winningNumbersGenerator = new WinningNumbersGeneratorTestImpl(Set.of(1, 2, 3, 4, 5, 100));
        WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = new WinningNumbersGeneratorConfiguration().createForTest(
                drawDateFacade,
                winningNumbersGenerator,
                winningNumbersTestRepository);
        //when
        //then
        assertThrows(WinningNumbersNotFoundException.class, winningNumbersGeneratorFacade::generateWinningNumbers, "Winning numbers out of range!");
    }
}
