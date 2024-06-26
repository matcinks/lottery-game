package org.mateusz.domain.resultchecker.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PlayersResultsDto(List<PlayerResultDto> playersResults,
                                String message) {
}