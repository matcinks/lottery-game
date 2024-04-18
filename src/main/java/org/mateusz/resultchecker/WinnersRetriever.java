package org.mateusz.resultchecker;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class WinnersRetriever {

    private final static int MIN_AMOUNT_OF_NUMBERS_TO_WON = 3;

    List<Player> retrieveWinners(List<Ticket> allTickets, Set<Integer> winningNumbers) {
        return allTickets.stream()
                .map(ticket -> {
                   Set<Integer> hitNumbers = calculateHitNumbers(ticket, winningNumbers);
                   return buildPlayer(ticket, hitNumbers);
                }).toList();
    }

    private Set<Integer> calculateHitNumbers(Ticket ticket, Set<Integer> winningNumbers) {
        return ticket.numbersFromUser()
                .stream()
                .filter(winningNumbers::contains)
                .collect(Collectors.toSet());
    }

    private Player buildPlayer(Ticket ticket, Set<Integer> hitNumbers) {
        boolean result = isWinner(hitNumbers);
        return Player.builder()
                .id(ticket.ticketId())
                .numbers(ticket.numbersFromUser())
                .hitNumbers(hitNumbers)
                .drawDate(ticket.drawDate())
                .isWinner(result)
                .build();
    }

    private boolean isWinner(Set<Integer> hitNumbers) {
        return hitNumbers.size() >= MIN_AMOUNT_OF_NUMBERS_TO_WON;
    }
}