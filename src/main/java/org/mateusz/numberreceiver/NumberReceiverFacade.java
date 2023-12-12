package org.mateusz.numberreceiver;

import lombok.AllArgsConstructor;
import org.mateusz.numberreceiver.dto.InputNumberResultDto;

import java.util.Collection;

@AllArgsConstructor
public class NumberReceiverFacade {

    private final NumberValidator validator;

    public InputNumberResultDto inputNumbers(Collection<Integer> numbersFromUser) {
        boolean areAllNumbersInRange = validator.areAllNumbersInRange(numbersFromUser);
        if (areAllNumbersInRange) {
            return InputNumberResultDto.builder()
                    .message("success")
                    .build();
        }
        return InputNumberResultDto.builder()
                .message("failure")
                .build();
    }

}
