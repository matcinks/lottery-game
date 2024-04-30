package org.mateusz.domain.resultchecker;

import lombok.AllArgsConstructor;
import org.mateusz.domain.numbergenerator.WinningNumbersGeneratorFacade;
import org.mateusz.domain.numberreceiver.NumberReceiverFacade;
import org.mateusz.domain.resultchecker.dto.PlayerResultDto;
import org.mateusz.domain.resultchecker.dto.PlayersResultsDto;
import org.mateusz.domain.numbergenerator.dto.WinningNumbersDto;
import org.mateusz.domain.numberreceiver.dto.TicketDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
public class ResultCheckerFacade {

    private final NumberReceiverFacade numberReceiverFacade;
    private final WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;
    private final WinnersRetriever winnersRetriever;
    private final PlayerRepository repository;

    public PlayersResultsDto generateWinners() {
        List<TicketDto> allTicketsDtosByNextDrawDate = numberReceiverFacade.retrieveAllTicketsForNextDrawDate();
        List<Ticket> allTicketsByNextDrawDate = ResultCheckerMapper.mapFromTicketDto(allTicketsDtosByNextDrawDate);
        if (allTicketsByNextDrawDate == null || allTicketsByNextDrawDate.isEmpty()) {
            return PlayersResultsDto.builder()
                    .message(ResultMessages.TICKETS_NOT_FOUND)
                    .build();
        }
        WinningNumbersDto winningNumbersDto = winningNumbersGeneratorFacade.generateWinningNumbers();
        Set<Integer> winningNumbers = winningNumbersDto.winningNumbers();
        if (winningNumbers == null || winningNumbers.isEmpty()) {
            return PlayersResultsDto.builder()
                    .message(ResultMessages.WINNERS_NOT_FOUND)
                    .build();
        }
        List<Player> winners = winnersRetriever.retrieveWinners(allTicketsByNextDrawDate, winningNumbers);
        repository.saveAll(winners);
        return PlayersResultsDto.builder()
                .playersResults(ResultCheckerMapper.mapFromPlayers(winners))
                .message(ResultMessages.TICKETS_FOUND)
                .build();
    }

    public PlayerResultDto findById(String id) {
        Player player = repository.findById(id)
                .orElseThrow(() -> new PlayerResultNotFoundException(String.format(ResultMessages.NO_PLAYER_FOR_ID, id)));
        return PlayerResultDto.builder()
                .id(player.id())
                .numbers(player.numbers())
                .hitNumbers(player.hitNumbers())
                .drawDate(player.drawDate())
                .isWinner(player.isWinner())
                .build();
    }
}