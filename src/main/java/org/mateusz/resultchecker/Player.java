package org.mateusz.resultchecker;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
record Player(String id,
              Set<Integer> numbers,
              Set<Integer> hitNumbers,
              LocalDateTime drawDate,
              boolean isWinner) {
}