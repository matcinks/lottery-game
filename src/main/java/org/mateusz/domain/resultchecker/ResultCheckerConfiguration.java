package org.mateusz.domain.resultchecker;

import org.mateusz.domain.numbergenerator.WinningNumbersGeneratorFacade;
import org.mateusz.domain.numberreceiver.NumberReceiverFacade;

public class ResultCheckerConfiguration {

    ResultCheckerFacade createForTest(NumberReceiverFacade numberReceiverFacade, WinningNumbersGeneratorFacade winningNumbersGeneratorFacade, PlayerRepository playerRepository) {
        WinnersRetriever winnersRetriever = new WinnersRetriever();
        return new ResultCheckerFacade(numberReceiverFacade, winningNumbersGeneratorFacade, winnersRetriever, playerRepository);
    }

}
