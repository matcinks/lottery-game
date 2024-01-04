package org.mateusz.resultchecker;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Collection;

@Builder
record Ticket(String ticketId,
              Collection<Integer> numbersFromUser,
              LocalDateTime drawDate) {
}
