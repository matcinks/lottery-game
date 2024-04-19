package org.mateusz.domain.numberreceiver;

import org.mateusz.domain.numberreceiver.dto.TicketDto;

class TicketMapper {

    public static TicketDto mapFromTicket(Ticket ticket) {
        return TicketDto.builder()
                .ticketId(ticket.ticketId())
                .numbers(ticket.numbersFromUser())
                .drawDate(ticket.drawDate())
                .build();
    }
}
