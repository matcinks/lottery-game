package org.mateusz.numberreceiver.dto;

import lombok.Builder;

@Builder
public record NumberReceiverResponseDto(TicketDto ticketDto,
                                        String message) {
}
