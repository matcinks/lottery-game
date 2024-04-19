package org.mateusz.domain.numberreceiver;

import org.mateusz.domain.drawdate.DrawDateFacade;

public class NumberReceiverConfiguration {

    NumberReceiverFacade createForTest(DrawDateFacade drawDateFacade, NumberReceiverRepository numberReceiverRepository) {
        NumberValidator numberValidator = new NumberValidator();
        return new NumberReceiverFacade(numberValidator,numberReceiverRepository, drawDateFacade);
    }
}
