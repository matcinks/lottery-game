package org.mateusz.domain.resultchecker.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record PlayerResultDto(String id,
                              Set<Integer> numbers,
                              Set<Integer> hitNumbers,
                              LocalDateTime drawDate,
                              boolean isWinner) {
}
