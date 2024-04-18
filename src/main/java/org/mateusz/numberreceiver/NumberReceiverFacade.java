package org.mateusz.numberreceiver;

import lombok.AllArgsConstructor;
import org.mateusz.drawdate.DrawDateFacade;
import org.mateusz.numberreceiver.dto.NumberReceiverResponseDto;
import org.mateusz.numberreceiver.dto.TicketDto;

import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
public class NumberReceiverFacade {

    private final NumberValidator validator;
    private final NumberReceiverRepository repository;
    private final DrawDateFacade drawDateFacade;

    public NumberReceiverResponseDto inputNumbers(Set<Integer> numbersFromUser) {
        boolean allNumbersAreInRange = validator.areAllNumbersInRange(numbersFromUser);
        if (allNumbersAreInRange) {
            String ticketId = UUID.randomUUID().toString();
            LocalDateTime drawDate = DrawDateMapper.drawDateTimeFromDrawDateDto(drawDateFacade.getNextDrawDate());
            TicketDto generatedTicket = TicketDto.builder()
                    .ticketId(ticketId)
                    .numbers(numbersFromUser)
                    .drawDate(drawDate)
                    .build();
            Ticket savedTicket = Ticket.builder()
                    .ticketId(ticketId)
                    .numbersFromUser(generatedTicket.numbers())
                    .drawDate(generatedTicket.drawDate())
                    .build();
            repository.save(savedTicket);
            return NumberReceiverResponseDto.builder()
                    .ticketDto(generatedTicket)
                    .message("success")
                    .build();
        }
        return new NumberReceiverResponseDto(null, "failure");
    }

    public List<TicketDto> retrieveAllTicketsForNextDrawDate(LocalDateTime date) {
        LocalDateTime nextDrawDate = DrawDateMapper.drawDateTimeFromDrawDateDto(drawDateFacade.getNextDrawDate());
        if (date.isAfter(nextDrawDate)) {
            return Collections.emptyList();
        }
        return repository.findAllTicketsByDrawDate(nextDrawDate)
                .stream()
                .map(TicketMapper::mapFromTicket)
                .toList();
    }

    public List<TicketDto> retrieveAllTicketsForNextDrawDate() {
        LocalDateTime nextDrawDate = DrawDateMapper.drawDateTimeFromDrawDateDto(drawDateFacade.getNextDrawDate());
        return retrieveAllTicketsForNextDrawDate(nextDrawDate);
    }
}