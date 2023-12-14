package org.mateusz.numberreceiver.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Collection;

@Builder
public record TicketDto(LocalDateTime drawDate, String ticketId, Collection<Integer> numbersFromUser) {
}
