package org.mateusz.resultchecker;

import java.util.Collection;
import java.util.List;

class WinnersRetriever {

    private final static int MIN_AMOUNT_OF_NUMBERS_TO_WON = 3;

    List<Player> retrieveWinners(List<Ticket> allTickets, Collection<Integer> winningNumbers) {
        return allTickets.stream()
                .map(ticket -> {

                   Collection<Integer> hitNumbers = ticket.numbersFromUser().stream()
                           .filter(winningNumbers::contains)
                           .toList();

                   return Player.builder()
                           .id(ticket.ticketId())
                           .numbers(ticket.numbersFromUser())
                           .drawDate(ticket.drawDate())
                           .hitNumbers(hitNumbers)
                           .isWinner(hitNumbers.size() > MIN_AMOUNT_OF_NUMBERS_TO_WON)
                           .build();

                }).toList();
    }

}
