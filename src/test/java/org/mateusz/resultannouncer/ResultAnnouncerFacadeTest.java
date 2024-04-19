package org.mateusz.resultannouncer;

import org.junit.jupiter.api.Test;
import org.mateusz.resultannouncer.dto.ResultAnnouncerResponseDto;
import org.mateusz.resultannouncer.dto.ResultDto;
import org.mateusz.resultchecker.PlayerResultNotFoundException;
import org.mateusz.resultchecker.ResultCheckerFacade;
import org.mateusz.resultchecker.dto.PlayerResultDto;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ResultAnnouncerFacadeTest {

    private final InMemoryResultAnnouncerRepositoryTestImpl repository = new InMemoryResultAnnouncerRepositoryTestImpl();
    private final ResultCheckerFacade resultCheckerFacade = mock(ResultCheckerFacade.class);

    @Test
    void should_return_cached_result() {
        // given
        String resultId = "001";
        Result cachedResult = Result.builder()
                .id(resultId)
                .build();
        repository.save(cachedResult);
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(repository, resultCheckerFacade);
        // when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(resultId);
        // then
        assertThat(resultAnnouncerResponseDto.resultDto()).isEqualTo(ResultMapper.mapFromResult(cachedResult));
        assertEquals(resultAnnouncerResponseDto.message(), ResultMessages.ALREADY_CHECKED);
    }

    @Test
    void should_return_null_result_and_not_found_message_if_there_is_no_ticked() {
        // given
        String id = "001";
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(repository, resultCheckerFacade);
        given(resultCheckerFacade.findById(id)).willThrow(new PlayerResultNotFoundException(org.mateusz.resultchecker.ResultMessages.NO_PLAYER_FOR_ID));
        // when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(id);
        // then
        String expectedMessage = String.format(ResultMessages.NOT_FOUND, id);
        assertThat(resultAnnouncerResponseDto.message()).isEqualTo(expectedMessage);
        assertThat(resultAnnouncerResponseDto.resultDto()).isNull();
    }

    @Test
    void should_return_correct_result_with_valid_message_when_player_won() {
        // given
        String id = "001";
        LocalDateTime drawDate = LocalDateTime.now();
        PlayerResultDto playerResultDto = PlayerResultDto.builder()
                .id(id)
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(repository, resultCheckerFacade);
        given(resultCheckerFacade.findById(id)).willReturn(playerResultDto);
        // when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(id);
        // then
        assertThat(resultAnnouncerResponseDto).isNotNull();
        ResultDto resultDto = resultAnnouncerResponseDto.resultDto();
        assertThat(resultDto.id()).isEqualTo(playerResultDto.id());
        assertThat(resultDto.numbers()).isEqualTo(playerResultDto.numbers());
        assertThat(resultDto.hitNumbers()).isEqualTo(playerResultDto.hitNumbers());
        assertThat(resultDto.drawDate()).isEqualTo(playerResultDto.drawDate());
        assertThat(resultDto.isWinner()).isEqualTo(playerResultDto.isWinner());
        assertThat(resultAnnouncerResponseDto.message()).isEqualTo(ResultMessages.WIN);
    }

    @Test
    void should_return_correct_result_with_valid_message_when_player_lost() {
        // given
        String id = "001";
        LocalDateTime drawDate = LocalDateTime.now();
        PlayerResultDto playerResultDto = PlayerResultDto.builder()
                .id(id)
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1))
                .drawDate(drawDate)
                .isWinner(false)
                .build();
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(repository, resultCheckerFacade);
        given(resultCheckerFacade.findById(id)).willReturn(playerResultDto);
        // when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(id);
        // then
        assertThat(resultAnnouncerResponseDto).isNotNull();
        ResultDto resultDto = resultAnnouncerResponseDto.resultDto();
        assertThat(resultDto.id()).isEqualTo(playerResultDto.id());
        assertThat(resultDto.numbers()).isEqualTo(playerResultDto.numbers());
        assertThat(resultDto.hitNumbers()).isEqualTo(playerResultDto.hitNumbers());
        assertThat(resultDto.drawDate()).isEqualTo(playerResultDto.drawDate());
        assertThat(resultDto.isWinner()).isEqualTo(playerResultDto.isWinner());
        assertThat(resultAnnouncerResponseDto.message()).isEqualTo(ResultMessages.LOSE);
    }

    @Test
    void should_return_correct_result_with_valid_message_when_drawing_was_not_yet_done() {
        // given
        String id = "001";
        int daysToShift = 1;
        LocalDateTime drawDate = LocalDateTime.now().plusDays(daysToShift);
        PlayerResultDto playerResultDto = PlayerResultDto.builder()
                .id(id)
                .drawDate(drawDate)
                .build();
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(repository, resultCheckerFacade);
        given(resultCheckerFacade.findById(id)).willReturn(playerResultDto);
        // when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(id);
        // then
        assertThat(resultAnnouncerResponseDto).isNotNull();
        ResultDto resultDto = resultAnnouncerResponseDto.resultDto();
        assertThat(resultDto.id()).isEqualTo(playerResultDto.id());
        assertThat(resultDto.numbers()).isEqualTo(playerResultDto.numbers());
        assertThat(resultDto.hitNumbers()).isEqualTo(playerResultDto.hitNumbers());
        assertThat(resultDto.drawDate()).isEqualTo(playerResultDto.drawDate());
        assertThat(resultDto.isWinner()).isEqualTo(playerResultDto.isWinner());
        String expectedMessage = String.format(ResultMessages.WAITING_MESSAGE, daysToShift);
        assertThat(resultAnnouncerResponseDto.message()).isEqualTo(expectedMessage);
    }
}