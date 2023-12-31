package org.mateusz.numberreceiver;

import org.junit.jupiter.api.Test;
import org.mateusz.drawdate.DrawDateFacade;
import org.mateusz.numberreceiver.dto.InputNumberResultDto;
import org.mateusz.numberreceiver.dto.TicketDto;

import java.time.*;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class NumberReceiverFacadeTest {

//    DrawDateFacade drawDateFacade = new DrawDateFacade(Clock.systemUTC());
    DrawDateFacade drawDateFacade = mock(DrawDateFacade.class);
    NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(
            new NumberValidator(),
            new InMemoryNumberReceiverRepositoryTestImpl(),
            drawDateFacade
    );

    @Test
    void should_return_success_when_user_gave_six_numbers_in_range() {
        // given
        Set<Integer> numbers = Set.of(1, 2, 3, 4, 5, 6);
        // when
        InputNumberResultDto actual = numberReceiverFacade.inputNumbers(numbers);
        // then
        assertThat(actual.message()).isEqualTo("success");
    }

    @Test
    void should_return_fail_when_user_gave_less_than_six_numbers() {
        //given
        Set<Integer> numbers = Set.of(1, 2, 3, 4, 5);
        //when
        InputNumberResultDto actual = numberReceiverFacade.inputNumbers(numbers);
        //then
        assertThat(actual.message()).isEqualTo("failure");
    }

    @Test
    void should_return_fail_when_user_gave_more_than_six_numbers() {
        //given
        Set<Integer> numbers = Set.of(1, 2, 3, 4, 5, 6, 7);
        //when
        InputNumberResultDto actual = numberReceiverFacade.inputNumbers(numbers);
        //then
        assertThat(actual.message()).isEqualTo("failure");
    }

    @Test
    void should_return_fail_when_user_gave_at_least_one_number_out_of_range_of_1_to_99() {
        //given
        Set<Integer> numbers = Set.of(0, 2, 3, 4, 5, 6);
        //when
        InputNumberResultDto actual = numberReceiverFacade.inputNumbers(numbers);
        //then
        assertThat(actual.message()).isEqualTo("failure");
    }

    @Test
    void should_return_fail_when_user_gave_at_least_two_same_numbers() {
        //given
        List<Integer> numbers = List.of(1, 1, 3, 4, 5, 6);
        //when
        InputNumberResultDto actual = numberReceiverFacade.inputNumbers(numbers);
        //then
        assertThat(actual.message()).isEqualTo("failure");
    }

    @Test
    void should_return_save_to_database_when_user_gave_six_numbers() {
        // given
        given(drawDateFacade.getNextDrawDate()).willReturn(LocalDateTime.now());
        Set<Integer> numbers = Set.of(1, 2, 3, 4, 5, 6);
        InputNumberResultDto actual = numberReceiverFacade.inputNumbers(numbers);
        LocalDateTime drawDate = drawDateFacade.getNextDrawDate();
        // when
        List<TicketDto> ticketDtos = numberReceiverFacade.userNumbers(drawDate);
        // then
        assertThat(ticketDtos).contains(
                TicketDto.builder()
                        .ticketId(actual.ticketId())
                        .drawDate(drawDate)
                        .numbersFromUser(actual.numbersFromUser())
                        .build()
        );
    }

}