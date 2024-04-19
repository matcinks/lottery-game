package org.mateusz.domain.resultannouncer;

import org.mateusz.domain.resultchecker.dto.PlayerResultDto;
import org.mateusz.domain.resultannouncer.dto.ResultDto;

public class ResultMapper {

    public static ResultDto mapFromResult(Result result) {
        return ResultDto.builder()
                .id(result.id())
                .numbers(result.numbers())
                .hitNumbers(result.hitNumbers())
                .drawDate(result.drawDate())
                .isWinner(result.isWinner())
                .build();
    }

    public static Result mapFromPlayerResultDto(PlayerResultDto playerResultDto) {
        return Result.builder()
                .id(playerResultDto.id())
                .numbers(playerResultDto.numbers())
                .hitNumbers(playerResultDto.hitNumbers())
                .drawDate(playerResultDto.drawDate())
                .isWinner(playerResultDto.isWinner())
                .build();
    }
}