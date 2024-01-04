package org.mateusz.resultchecker;

import org.junit.jupiter.api.Test;
import org.mateusz.numbergenerator.WinningNumbersGeneratorFacade;
import org.mateusz.numberreceiver.NumberReceiverFacade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ResultCheckerFacadeTest {

    private final NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
    private final WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = mock(WinningNumbersGeneratorFacade.class);

    @Test
    void should_return_correct_winners() {
        //given
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().createForTest(numberReceiverFacade, winningNumbersGeneratorFacade);
        //when
        String winnersList = resultCheckerFacade.generateWinners();
        //then
        assertThat(winnersList).isEqualTo("Winners list");
    }

}