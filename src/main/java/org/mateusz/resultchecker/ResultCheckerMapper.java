package org.mateusz.resultchecker;

import org.mateusz.numberreceiver.dto.TicketDto;

import java.util.List;

class ResultCheckerMapper {

    static List<Ticket> mapFromTicketDto(List<TicketDto> ticketDtoList) {
        return ticketDtoList.stream()
                .map(ticketDto -> Ticket.builder()
                        .ticketId(ticketDto.ticketId())
                        .numbersFromUser(ticketDto.numbers())
                        .drawDate(ticketDto.drawDate())
                        .build())
                .toList();
    }

}
