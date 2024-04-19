package org.mateusz.resultchecker;

import org.junit.jupiter.api.Test;
import org.mateusz.numbergenerator.WinningNumbersGeneratorFacade;
import org.mateusz.numbergenerator.dto.WinningNumbersDto;
import org.mateusz.numberreceiver.NumberReceiverFacade;
import org.mateusz.numberreceiver.dto.TicketDto;
import org.mateusz.resultchecker.dto.PlayerResultDto;
import org.mateusz.resultchecker.dto.PlayersResultsDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ResultCheckerFacadeTest {

    private final NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
    private final WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = mock(WinningNumbersGeneratorFacade.class);
    private final InMemoryPlayerResultRepositoryTestImpl repository = new InMemoryPlayerResultRepositoryTestImpl();

    @Test
    void should_return_correct_winners() {
        //given
        LocalDateTime drawDate = LocalDateTime.now();

        TicketDto ticketDto1 = createTicket("001", Set.of(1, 2, 3, 7, 8, 9), drawDate);
        TicketDto ticketDto2 = createTicket("002", Set.of(1, 2, 3, 4, 5, 6), drawDate);
        TicketDto ticketDto3 = createTicket("003", Set.of(7, 8, 9, 10, 11, 12), drawDate);

        WinningNumbersDto winningNumbersDto = WinningNumbersDto.builder()
                .winningNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .build();

        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().createForTest(numberReceiverFacade, winningNumbersGeneratorFacade, repository);

        given(numberReceiverFacade.retrieveAllTicketsForNextDrawDate()).willReturn(List.of(ticketDto1, ticketDto2, ticketDto3));
        given(winningNumbersGeneratorFacade.generateWinningNumbers()).willReturn(winningNumbersDto);

        //when
        PlayersResultsDto playersResultsDto = resultCheckerFacade.generateWinners();

        //then
        List<PlayerResultDto> listOfPlayersResultsDto = playersResultsDto.playersResults();

        PlayerResultDto playerResultDto1 = createPlayerResult("001", Set.of(1, 2, 3, 7, 8, 9), Set.of(1, 2, 3), drawDate, true);
        PlayerResultDto playerResultDto2 = createPlayerResult("002", Set.of(1, 2, 3, 4, 5, 6), Set.of(1, 2, 3, 4, 5, 6), drawDate, true);
        PlayerResultDto playerResultDto3 = createPlayerResult("003", Set.of(7, 8, 9, 10, 11, 12), Set.of(), drawDate, false);

        assertThat(listOfPlayersResultsDto).contains(playerResultDto1, playerResultDto2, playerResultDto3);
        assertThat(playersResultsDto.message()).isEqualTo(ResultMessages.TICKETS_FOUND);
    }

    private TicketDto createTicket(String id, Set<Integer> numbers, LocalDateTime drawDate) {
        return TicketDto.builder()
                .ticketId(id)
                .numbers(numbers)
                .drawDate(drawDate)
                .build();
    }

    private PlayerResultDto createPlayerResult(String id, Set<Integer> numbers, Set<Integer> hitNumbers, LocalDateTime drawDate, boolean isWinner) {
        return PlayerResultDto.builder()
                .id(id)
                .numbers(numbers)
                .hitNumbers(hitNumbers)
                .drawDate(drawDate)
                .isWinner(isWinner)
                .build();
    }

    @Test
    void should_return_failed_message_when_there_are_no_tickets() {
        // given
        given(numberReceiverFacade.retrieveAllTicketsForNextDrawDate()).willReturn(List.of());
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().createForTest(numberReceiverFacade, winningNumbersGeneratorFacade, repository);
        // when
        PlayersResultsDto playersResultsDto = resultCheckerFacade.generateWinners();
        // then
        assertThat(playersResultsDto.message()).isEqualTo(ResultMessages.TICKETS_NOT_FOUND);
        assertThat(playersResultsDto.playersResults()).isNull();
    }

    @Test
    void should_return_failed_message_when_there_are_no_winning_numbers() {
        // given
        TicketDto ticketDto = createTicket("001", Set.of(1, 2, 3, 7, 8, 9), LocalDateTime.now());
        given(numberReceiverFacade.retrieveAllTicketsForNextDrawDate()).willReturn(List.of(ticketDto));
        given(winningNumbersGeneratorFacade.generateWinningNumbers()).willReturn(WinningNumbersDto.builder().build());
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().createForTest(numberReceiverFacade, winningNumbersGeneratorFacade, repository);
        // when
        PlayersResultsDto playersResultsDto = resultCheckerFacade.generateWinners();
        // then
        assertThat(playersResultsDto.message()).isEqualTo(ResultMessages.WINNERS_NOT_FOUND);
        assertThat(playersResultsDto.playersResults()).isNull();
    }

    @Test
    void should_return_player_result_dto_when_player_exists() {
        // given
        String expectedId = "001";
        Set<Integer> numbers = Set.of(1, 2, 3, 7, 8, 9);
        Set<Integer> hitNumbers = Set.of(1, 2, 3);
        LocalDateTime drawDate = LocalDateTime.now();
        boolean isWinner = true;
        Player player = new Player(expectedId, numbers, hitNumbers, drawDate, isWinner);
        repository.saveAll(List.of(player));

        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().createForTest(numberReceiverFacade, winningNumbersGeneratorFacade, repository);

        // when
        PlayerResultDto playerResultDto = resultCheckerFacade.findById(expectedId);

        // then
        assertEquals(expectedId, playerResultDto.id());
        assertEquals(numbers, playerResultDto.numbers());
        assertEquals(hitNumbers, playerResultDto.hitNumbers());
        assertEquals(drawDate, playerResultDto.drawDate());
        assertEquals(isWinner, playerResultDto.isWinner());
    }

    @Test
    void should_throw_exception_when_player_does_not_exist() {
        // given
        String nonExistentId = "nonExistentId";
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().createForTest(numberReceiverFacade, winningNumbersGeneratorFacade, repository);
        // when & then
        assertThrows(PlayerResultNotFoundException.class, () -> resultCheckerFacade.findById(nonExistentId));
    }
}