package org.mateusz.resultchecker;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
record Ticket(String ticketId,
              Set<Integer> numbersFromUser,
              LocalDateTime drawDate) {
}
