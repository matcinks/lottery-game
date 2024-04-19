package org.mateusz.numberreceiver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mateusz.AdjustableClock;
import org.mateusz.domain.drawdate.DrawDateFacade;
import org.mateusz.domain.drawdate.dto.DrawDateDto;
import org.mateusz.domain.numberreceiver.DrawDateMapper;
import org.mateusz.domain.numberreceiver.NumberReceiverConfiguration;
import org.mateusz.domain.numberreceiver.NumberReceiverFacade;
import org.mateusz.domain.numberreceiver.dto.NumberReceiverResponseDto;
import org.mateusz.domain.numberreceiver.dto.TicketDto;

import java.time.*;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NumberReceiverFacadeTest {

    private static final DrawDateDto DUMMY_DRAW_DATE_DTO = DrawDateDto.builder()
            .id("001")
            .time(LocalDateTime.now())
            .build();
    private final DrawDateFacade drawDateFacade = mock(DrawDateFacade.class);
    private final InMemoryNumberReceiverRepositoryTestImpl numberReceiverTestRepository = new InMemoryNumberReceiverRepositoryTestImpl();

    @BeforeEach
    public void setUp() {
        when(drawDateFacade.getNextDrawDate()).thenReturn(DUMMY_DRAW_DATE_DTO);
    }

    @Test
    void should_return_success_when_user_gave_six_numbers_in_range() {
        // given
        Set<Integer> numbers = Set.of(1, 2, 3, 4, 5, 6);
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTest(drawDateFacade, numberReceiverTestRepository);
        // when
        NumberReceiverResponseDto actual = numberReceiverFacade.inputNumbers(numbers);
        // then
        assertThat(actual.message()).isEqualTo("success");
    }

    @Test
    void should_return_fail_when_user_gave_less_than_six_numbers() {
        //given
        Set<Integer> numbers = Set.of(1, 2, 3, 4, 5);
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTest(drawDateFacade, numberReceiverTestRepository);
        //when
        NumberReceiverResponseDto actual = numberReceiverFacade.inputNumbers(numbers);
        //then
        assertThat(actual.message()).isEqualTo("failure");
    }

    @Test
    void should_return_fail_when_user_gave_more_than_six_numbers() {
        //given
        Set<Integer> numbers = Set.of(1, 2, 3, 4, 5, 6, 7);
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTest(drawDateFacade, numberReceiverTestRepository);
        //when
        NumberReceiverResponseDto actual = numberReceiverFacade.inputNumbers(numbers);
        //then
        assertThat(actual.message()).isEqualTo("failure");
    }

    @Test
    void should_return_fail_when_user_gave_at_least_one_number_out_of_range_of_1_to_99() {
        //given
        Set<Integer> numbers = Set.of(0, 2, 3, 4, 5, 6);
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTest(drawDateFacade, numberReceiverTestRepository);
        //when
        NumberReceiverResponseDto actual = numberReceiverFacade.inputNumbers(numbers);
        //then
        assertThat(actual.message()).isEqualTo("failure");
    }

    @Test
    void should_return_save_to_database_when_user_gave_six_numbers() {
        // given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTest(drawDateFacade, numberReceiverTestRepository);
        TicketDto actual = generateTicketDto(numberReceiverFacade, Set.of(1, 2, 3, 4, 5, 6));
        LocalDateTime drawDate = drawDateFacade.getNextDrawDate()
                .time();
        // when
        List<TicketDto> ticketDtos = numberReceiverFacade.retrieveAllTicketsForNextDrawDate(drawDate);
        // then
        assertThat(ticketDtos).contains(
                TicketDto.builder()
                        .ticketId(actual.ticketId())
                        .numbers(actual.numbers())
                        .drawDate(drawDate)
                        .build()
        );
    }

    @Test
    void should_return_empty_collection_if_there_are_no_tickets() {
        // given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTest(drawDateFacade, numberReceiverTestRepository);
        LocalDateTime drawDate = DrawDateMapper.drawDateTimeFromDrawDateDto(drawDateFacade.getNextDrawDate());
        // when
        List<TicketDto> listOfTickets = numberReceiverFacade.retrieveAllTicketsForNextDrawDate(drawDate);
        // then
        assertThat(listOfTickets).isEmpty();
    }

    @Test
    void should_return_empty_collection_when_provided_draw_date_is_one_week_after_current_draw_date() {
        // given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTest(drawDateFacade, numberReceiverTestRepository);

        Set<Integer> ticketNumbers = Set.of(1, 2, 3, 4, 5, 6);
        numberReceiverFacade.inputNumbers(ticketNumbers);

        LocalDateTime drawDate = DrawDateMapper.drawDateTimeFromDrawDateDto(drawDateFacade.getNextDrawDate());
        LocalDateTime nextWeekDrawDate = drawDate.plusWeeks(1);
        // when
        List<TicketDto> listOfTickets = numberReceiverFacade.retrieveAllTicketsForNextDrawDate(nextWeekDrawDate);
        // then
        assertThat(listOfTickets).isEmpty();
    }

    @Test
    void should_return_all_tickets_for_draw_date_next_to_forwarded_date() {
        // given
        AdjustableClock clock = new AdjustableClock(Instant.now(), ZoneId.of("UTC"));
        LocalDateTime currentTime = LocalDateTime.now(clock);
        final int NEXT_DAY = 1;
        final int ONE_WEEK = 7;

        DrawDateDto drawDateDto = createDrawDateDto(clock, NEXT_DAY);
        DrawDateDto drawDateDtoForWeekLater = createDrawDateDto(clock, ONE_WEEK);

        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTest(drawDateFacade, numberReceiverTestRepository);

        given(drawDateFacade.getNextDrawDate()).willReturn(drawDateDto,
                drawDateDto,
                drawDateDtoForWeekLater,
                drawDateDto,
                drawDateDtoForWeekLater);

        TicketDto firstTicketDto = generateTicketDto(numberReceiverFacade, Set.of(1, 2, 3, 4, 5, 6));
        TicketDto secondTicketDto = generateTicketDto(numberReceiverFacade, Set.of(7, 8, 9, 10, 11, 12));
        List<TicketDto> listOfTicketsDtos = List.of(firstTicketDto, secondTicketDto);

        TicketDto thirdTicketDto = generateTicketDto(numberReceiverFacade, Set.of(6, 5, 4, 3, 2, 1));

        // when
        List<TicketDto> retrievedFirstDrawingTicketsDtos = numberReceiverFacade.retrieveAllTicketsForNextDrawDate(currentTime);
        List<TicketDto> retrievedSecondDrawingTicketsDtos = numberReceiverFacade.retrieveAllTicketsForNextDrawDate();

        // then
        assertThat(retrievedFirstDrawingTicketsDtos).containsExactlyInAnyOrderElementsOf(listOfTicketsDtos);
        assertThat(retrievedFirstDrawingTicketsDtos).doesNotContain(thirdTicketDto);
        assertThat(retrievedSecondDrawingTicketsDtos).contains(thirdTicketDto);
    }

    private DrawDateDto createDrawDateDto(AdjustableClock clock, int days) {
        clock.plusDays(days);
        return DrawDateDto.builder()
                .time(LocalDateTime.now(clock))
                .build();
    }

    private TicketDto generateTicketDto(NumberReceiverFacade numberReceiverFacade, Set<Integer> ticketNumbers) {
        return numberReceiverFacade.inputNumbers(ticketNumbers).ticketDto();
    }
}