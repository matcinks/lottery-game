package org.mateusz.infrastructure.numberreceiver.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mateusz.domain.numberreceiver.NumberReceiverFacade;
import org.mateusz.domain.numberreceiver.dto.NumberReceiverResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@Log4j2
@AllArgsConstructor
public class InputNumbersRestController {

    private final NumberReceiverFacade numberReceiverFacade;

    @PostMapping("/inputNumbers")
    public ResponseEntity<NumberReceiverResponseDto> inputNumbers(@RequestBody InputNumbersRequestDto requestDto) {
        log.info(requestDto.inputNumbers());
        Set<Integer> integers = new HashSet<>(requestDto.inputNumbers());
        NumberReceiverResponseDto numberReceiverResponseDto = numberReceiverFacade.inputNumbers(integers);
        return ResponseEntity.ok(numberReceiverResponseDto);
    }
}
