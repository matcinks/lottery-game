package org.mateusz.numberreceiver;

import org.junit.jupiter.api.Test;
import org.mateusz.numberreceiver.dto.InputNumberResultDto;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class NumberReceiverFacadeTest {

    NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(new NumberValidator());

    @Test
    void should_return_success_when_user_gave_six_numbers() {
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

}