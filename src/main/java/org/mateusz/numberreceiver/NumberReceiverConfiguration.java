package org.mateusz.numberreceiver;

import org.mateusz.drawdate.DrawDateFacade;

public class NumberReceiverConfiguration {

    NumberReceiverFacade createForTest(DrawDateFacade drawDateFacade, NumberReceiverRepository numberReceiverRepository) {
        NumberValidator numberValidator = new NumberValidator();
        return new NumberReceiverFacade(numberValidator,numberReceiverRepository, drawDateFacade);
    }
}
