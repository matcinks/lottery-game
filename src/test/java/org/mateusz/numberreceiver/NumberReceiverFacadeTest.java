package org.mateusz.numberreceiver;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class NumberReceiverFacadeTest {

    NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade();

    @Test
    public void should_return_success_when_user_gave_six_numbers() {
        // given
        Set<Integer> numbers = Set.of(
                1, 2, 3, 4, 5, 6
        );
        // when
        String actual = numberReceiverFacade.inputNumbers(numbers);
        // then
        assertThat(actual).isEqualTo("OK");
    }
    @Test
    public void should_return_fail_when_user_gave_less_than_six_numbers() {
    }

    @Test
    public void should_return_fail_when_user_gave_more_than_six_numbers() {
    }


}
