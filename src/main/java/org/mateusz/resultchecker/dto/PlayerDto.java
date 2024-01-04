package org.mateusz.resultchecker.dto;

import java.time.LocalDateTime;
import java.util.Collection;

public record PlayerDto(Collection<Integer> numbers,
                        Collection<Integer> hitNumbers,
                        LocalDateTime drawDate,
                        boolean isWinner) {
}
