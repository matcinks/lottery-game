package org.mateusz.domain.resultchecker;

import org.mateusz.domain.resultchecker.dto.PlayerResultDto;
import org.mateusz.domain.numberreceiver.dto.TicketDto;

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

    static List<PlayerResultDto> mapFromPlayers(List<Player> players) {
        return players.stream()
                .map(ResultCheckerMapper::mapFromPlayer)
                .toList();
    }

    static PlayerResultDto mapFromPlayer(Player player) {
        return PlayerResultDto.builder()
                .id(player.id())
                .numbers(player.numbers())
                .hitNumbers(player.hitNumbers())
                .drawDate(player.drawDate())
                .isWinner(player.isWinner())
                .build();
    }
}