package org.mateusz.resultchecker;

import lombok.AllArgsConstructor;
import org.mateusz.numbergenerator.WinningNumbersGeneratorFacade;
import org.mateusz.numbergenerator.dto.WinningNumbersDto;
import org.mateusz.numberreceiver.NumberReceiverFacade;
import org.mateusz.numberreceiver.dto.TicketDto;
import org.mateusz.resultchecker.dto.PlayerResultDto;
import org.mateusz.resultchecker.dto.PlayersResultsDto;

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
        Optional<Player> player = repository.findById(id);
        if (player.isPresent()) {
            return ResultCheckerMapper.mapFromPlayer(player.get());
        } else {
            throw new PlayerResultNotFoundException(String.format(ResultMessages.NO_PLAYER_FOR_ID, id));
        }
    }
}