package org.mateusz.numberreceiver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mateusz.drawdate.DrawDateConfiguration;
import org.mateusz.drawdate.DrawDateFacade;
import org.mateusz.numberreceiver.dto.NumberReceiverResponseDto;
import org.mateusz.numberreceiver.dto.TicketDto;

import java.time.*;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class NumberReceiverFacadeTest {

    private static final ZonedDateTime NOW = ZonedDateTime.of(
            2024,
            1,
            3,
            12,
            0,
            0,
            0,
            ZoneId.of("GMT+1")
    );
    private final Clock fixedClock = mock(Clock.class);
    private final DrawDateFacade drawDateFacade = new DrawDateConfiguration().createForTest(fixedClock);
    private final InMemoryNumberReceiverRepositoryTestImpl numberReceiverTestRepository = new InMemoryNumberReceiverRepositoryTestImpl();

    @BeforeEach
    void setUp() {
        given(fixedClock.getZone()).willReturn(NOW.getZone());
        given(fixedClock.instant()).willReturn(NOW.toInstant());
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
    void should_return_fail_when_user_gave_at_least_two_same_numbers() {
        //given
        List<Integer> numbers = List.of(1, 1, 3, 4, 5, 6);
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTest(drawDateFacade, numberReceiverTestRepository);
        //when
        NumberReceiverResponseDto actual = numberReceiverFacade.inputNumbers(numbers);
        //then
        assertThat(actual.message()).isEqualTo("failure");
    }

    @Test
    void should_return_save_to_database_when_user_gave_six_numbers() {
        // given
        Set<Integer> numbers = Set.of(1, 2, 3, 4, 5, 6);
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTest(drawDateFacade, numberReceiverTestRepository);
        TicketDto actual = numberReceiverFacade.inputNumbers(numbers).ticketDto();
        LocalDateTime drawDate = drawDateFacade.getNextDrawDate();
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
    void should_return_correct_tickets_for_next_draw_date() {
        // given
        Instant sameDayInstant = NOW.toInstant();
        Instant nextDayInstant = NOW.plusDays(1).toInstant();
        Instant nextWeekInstant = NOW.plusWeeks(1).toInstant();

        given(fixedClock.instant()).willReturn(sameDayInstant, nextDayInstant, nextWeekInstant, sameDayInstant);

        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTest(drawDateFacade, numberReceiverTestRepository);

        Set<Integer> firstTicketNumbers = Set.of(1, 2, 3, 4, 5, 6);
        Set<Integer> secondTicketNumbers = Set.of(7, 8, 9, 10, 11, 12);
        Set<Integer> thirdTicketNumbers = Set.of(1, 2, 3, 10, 11, 12);

        NumberReceiverResponseDto numberReceiverResponseDto1 = numberReceiverFacade.inputNumbers(firstTicketNumbers);
        NumberReceiverResponseDto numberReceiverResponseDto2 = numberReceiverFacade.inputNumbers(secondTicketNumbers);
        NumberReceiverResponseDto numberReceiverResponseDto3 = numberReceiverFacade.inputNumbers(thirdTicketNumbers);
        TicketDto ticketDto1 = numberReceiverResponseDto1.ticketDto();
        TicketDto ticketDto2 = numberReceiverResponseDto2.ticketDto();
        TicketDto ticketDto3 = numberReceiverResponseDto3.ticketDto();
        // when
        List<TicketDto> listOfTickets = numberReceiverFacade.retrieveAllTicketsForNextDrawDate();
        // then
        assertThat(listOfTickets)
                .hasSize(2)
                .containsAll(List.of(ticketDto1, ticketDto2))
                .doesNotContain(ticketDto3);
    }

    @Test
    void should_return_empty_collection_if_there_ara_no_tickets() {
        // given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTest(drawDateFacade, numberReceiverTestRepository);
        LocalDateTime drawDate = drawDateFacade.getNextDrawDate();
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

        LocalDateTime drawDate = drawDateFacade.getNextDrawDate();
        LocalDateTime nextWeekDrawDate = drawDate.plusWeeks(1);
        // when
        List<TicketDto> listOfTickets = numberReceiverFacade.retrieveAllTicketsForNextDrawDate(nextWeekDrawDate);
        // then
        assertThat(listOfTickets).isEmpty();
    }

}