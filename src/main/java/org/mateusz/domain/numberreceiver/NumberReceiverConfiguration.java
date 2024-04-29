package org.mateusz.domain.numberreceiver;

import org.mateusz.domain.drawdate.DrawDateFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NumberReceiverConfiguration {

    @Bean
    NumberReceiverFacade numberReceiverFacade(DrawDateFacade drawDateFacade, NumberReceiverRepository numberReceiverRepository) {
        NumberValidator numberValidator = new NumberValidator();
        return new NumberReceiverFacade(numberValidator, numberReceiverRepository, drawDateFacade);
    }
}
