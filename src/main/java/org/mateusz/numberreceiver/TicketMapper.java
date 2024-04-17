package org.mateusz.numberreceiver;

import org.mateusz.numberreceiver.dto.TicketDto;

class TicketMapper {

    public static TicketDto mapFromTicket(Ticket ticket) {
        return TicketDto.builder()
                .ticketId(ticket.ticketId())
                .numbers(ticket.numbersFromUser())
                .drawDate(ticket.drawDate())
                .build();
    }

    public static Ticket mapFromTicketDto(TicketDto ticketDto) {
        return Ticket.builder()
                .ticketId(ticketDto.ticketId())
                .numbersFromUser(ticketDto.numbers())
                .drawDate(ticketDto.drawDate())
                .build();
    }
}
