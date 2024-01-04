package org.mateusz.numberreceiver.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Collection;

@Builder
public record TicketDto(String ticketId,
                        Collection<Integer> numbers,
                        LocalDateTime drawDate) {
}
