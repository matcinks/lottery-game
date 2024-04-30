package org.mateusz.domain.resultannouncer;

import lombok.AllArgsConstructor;
import org.mateusz.domain.resultchecker.PlayerResultNotFoundException;
import org.mateusz.domain.resultchecker.ResultCheckerFacade;
import org.mateusz.domain.resultchecker.dto.PlayerResultDto;
import org.mateusz.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import org.mateusz.domain.resultannouncer.dto.ResultDto;

import java.util.Optional;

@AllArgsConstructor
public class ResultAnnouncerFacade {

    private final ResultAnnouncerRepository repository;
    private final ResultCheckerFacade resultCheckerFacade;
    private final ResultProcessor resultProcessor;

    public ResultAnnouncerResponseDto checkResult(String id) {
        Optional<Result> cachedResult = repository.findById(id);
        if (cachedResult.isPresent()) {
            return buildResponse(cachedResult.get(), ResultMessages.ALREADY_CHECKED);
        }
        PlayerResultDto playerResultDto = resultCheckerFacade.findById(id);
        Result result = ResultMapper.mapFromPlayerResultDto(playerResultDto);
        Result savedResult = repository.save(result);
        String resultMessage = resultProcessor.process(savedResult);
        return buildResponse(savedResult, resultMessage);
    }

    private ResultAnnouncerResponseDto buildResponse(Result result, String message) {
        ResultDto resultDto = Optional.ofNullable(result)
                .map(ResultMapper::mapFromResult)
                .orElse(null);
        return ResultAnnouncerResponseDto.builder()
                .resultDto(resultDto)
                .message(message)
                .build();
    }
}