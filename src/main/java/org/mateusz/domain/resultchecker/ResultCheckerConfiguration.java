package org.mateusz.domain.resultchecker;

import org.mateusz.domain.numbergenerator.WinningNumbersGeneratorFacade;
import org.mateusz.domain.numberreceiver.NumberReceiverFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResultCheckerConfiguration {

    @Bean
    ResultCheckerFacade resultCheckerFacade(NumberReceiverFacade numberReceiverFacade, WinningNumbersGeneratorFacade winningNumbersGeneratorFacade, PlayerRepository playerRepository) {
        WinnersRetriever winnersRetriever = new WinnersRetriever();
        return new ResultCheckerFacade(numberReceiverFacade, winningNumbersGeneratorFacade, winnersRetriever, playerRepository);
    }
}
