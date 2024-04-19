package org.mateusz.numbergenerator;

import org.mateusz.drawdate.DrawDateFacade;

public class WinningNumbersGeneratorConfiguration {

    WinningNumbersGeneratorFacade createForTest(DrawDateFacade drawDateFacade,
                                                RandomNumberGenerator winningNumbersGenerator,
                                                WinningNumbersRepository winningNumbersRepository) {
        WinningNumbersValidator winningNumbersValidator = new WinningNumbersValidator();
        return new WinningNumbersGeneratorFacade(drawDateFacade, winningNumbersGenerator, winningNumbersValidator, winningNumbersRepository);
    }

}
