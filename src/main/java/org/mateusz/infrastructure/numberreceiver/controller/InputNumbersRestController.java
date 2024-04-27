package org.mateusz.infrastructure.numberreceiver.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class InputNumbersRestController {

    @PostMapping("/inputNumbers")
    public ResponseEntity<String> inputNumbers(@RequestBody InputNumbersRequestDto requestDto) {
        log.info(requestDto.inputNumbers());
        return ResponseEntity.ok("hehe");
    }
}
