package org.mateusz.resultannouncer;

import lombok.AllArgsConstructor;
import org.mateusz.resultannouncer.dto.ResultAnnouncerResponseDto;
import org.mateusz.resultannouncer.dto.ResultDto;
import org.mateusz.resultchecker.PlayerResultNotFoundException;
import org.mateusz.resultchecker.ResultCheckerFacade;
import org.mateusz.resultchecker.dto.PlayerResultDto;

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
          try {
               PlayerResultDto playerResultDto = resultCheckerFacade.findById(id);
               Result result = ResultMapper.mapFromPlayerResultDto(playerResultDto);
               Result savedResult = repository.save(result);
               String resultMessage = resultProcessor.process(savedResult);
               return buildResponse(savedResult, resultMessage);
          } catch (PlayerResultNotFoundException ex) {
               return buildResponse(null, String.format(ResultMessages.NOT_FOUND, id));
          }
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