package org.mateusz.resultchecker;

import lombok.AllArgsConstructor;
import org.mateusz.numbergenerator.WinningNumbersGeneratorFacade;
import org.mateusz.numbergenerator.dto.WinningNumbersDto;
import org.mateusz.numberreceiver.NumberReceiverFacade;
import org.mateusz.numberreceiver.dto.TicketDto;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class ResultCheckerFacade {

    NumberReceiverFacade numberReceiverFacade;
    WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;
    WinnersRetriever winnersRetriever;

    public String generateWinners() {

        // do I need this object? maybe I can call numberReceiverFacade method directly and then map result to Ticket object
        List<TicketDto> allTicketsByNextDrawDate = numberReceiverFacade.retrieveAllTicketsForNextDrawDate();
        List<Ticket> ticketsList = ResultCheckerMapper.mapFromTicketDto(allTicketsByNextDrawDate);

        WinningNumbersDto winningNumbersDto = winningNumbersGeneratorFacade.generateWinningNumbers();
        Collection<Integer> winningNumbers = winningNumbersDto.winningNumbers();

        if (winningNumbers == null || winningNumbers.isEmpty()) {
            return "Failed to retrieve winners";
        }

        List<Player> winners = winnersRetriever.retrieveWinners(ticketsList, winningNumbers);

        return "Winners retrieve successfully";
    }

    public String findById(String id) {
        return "Found id";
    }

}
