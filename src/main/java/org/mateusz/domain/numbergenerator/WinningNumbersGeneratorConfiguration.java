package org.mateusz.domain.numbergenerator;

import org.mateusz.domain.drawdate.DrawDateFacade;

public class WinningNumbersGeneratorConfiguration {

    WinningNumbersGeneratorFacade createForTest(DrawDateFacade drawDateFacade,
                                                RandomNumberGenerable winningNumbersGenerator,
                                                WinningNumbersRepository winningNumbersRepository) {
        WinningNumbersValidator winningNumbersValidator = new WinningNumbersValidator();
        return new WinningNumbersGeneratorFacade(drawDateFacade, winningNumbersGenerator, winningNumbersValidator, winningNumbersRepository);
    }

}
