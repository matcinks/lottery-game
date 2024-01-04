package org.mateusz.resultchecker;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Collection;

@Builder
record Player(String id,
                     Collection<Integer> numbers,
                     Collection<Integer> hitNumbers,
                     LocalDateTime drawDate,
                     boolean isWinner) {
}