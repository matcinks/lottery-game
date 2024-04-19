package org.mateusz.resultannouncer;

import org.mateusz.resultannouncer.dto.ResultDto;
import org.mateusz.resultchecker.dto.PlayerResultDto;

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