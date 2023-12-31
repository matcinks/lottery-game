package org.mateusz.numbergenerator.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Collection;

@Builder
public record WinningNumbersDto(Collection<Integer> winningNumbers, LocalDateTime drawDate) {
}
