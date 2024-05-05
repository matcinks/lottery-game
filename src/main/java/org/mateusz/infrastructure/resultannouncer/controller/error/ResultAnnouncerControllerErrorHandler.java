package org.mateusz.infrastructure.resultannouncer.controller.error;

import lombok.extern.log4j.Log4j2;
import org.mateusz.domain.resultchecker.PlayerResultNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@RestControllerAdvice
@Log4j2
public class ResultAnnouncerControllerErrorHandler {

    @ExceptionHandler(PlayerResultNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResultAnnouncerErrorResponse handleResultNotFound(PlayerResultNotFoundException exception) {
        String message = exception.getMessage();
        log.error(message);
        return new ResultAnnouncerErrorResponse(message, HttpStatus.NOT_FOUND);
    }
}
