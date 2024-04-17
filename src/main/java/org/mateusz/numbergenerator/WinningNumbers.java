package org.mateusz.numbergenerator;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Collection;

@Builder
record WinningNumbers(String id,
                      Collection<Integer> winningNumbers,
                      LocalDateTime drawDate) {
}
