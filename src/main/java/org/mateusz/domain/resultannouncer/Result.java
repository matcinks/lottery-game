package org.mateusz.domain.resultannouncer;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record Result(String id,
                     Set<Integer> numbers,
                     Set<Integer> hitNumbers,
                     LocalDateTime drawDate,
                     boolean isWinner) {
}