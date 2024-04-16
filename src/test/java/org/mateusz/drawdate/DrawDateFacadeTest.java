package org.mateusz.drawdate;

import org.junit.jupiter.api.Test;
import org.mateusz.AdjustableClock;
import org.mateusz.drawdate.dto.DrawDateDto;

import java.time.*;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DrawDateFacadeTest {

    private final DrawDateRepository drawDateRepository = new InMemoryDrawDateRepositoryTestImpl();

    @Test
    void should_return_expected_next_draw_date() {
        //given
        LocalDateTime fridayAtNoon = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.FRIDAY))
                .withHour(12)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        Instant fridayNoonInstant = fridayAtNoon.toInstant(ZoneOffset.UTC);
        Clock clock = Clock.fixed(fridayNoonInstant, ZoneId.of("UTC"));
        DrawDateFacade drawDateFacade = new DrawDateConfiguration().createForTest(clock, drawDateRepository);
        //when
        DrawDateDto drawDateDto = drawDateFacade.getNextDrawDate();
        //then
        LocalDateTime expectedDrawDate = fridayAtNoon
                .with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        assertThat(drawDateDto.time()).isEqualTo(expectedDrawDate);
    }

    @Test
    void should_return_expected_previous_draw_date() {
        //given
        LocalDateTime saturdayBeforeNoon = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
                .withHour(11)
                .withMinute(59)
                .withSecond(59)
                .withNano(0);
        Instant fridayNoonInstant = saturdayBeforeNoon.toInstant(ZoneOffset.UTC);
        AdjustableClock clock = new AdjustableClock(fridayNoonInstant, ZoneId.of("UTC"));
        DrawDateFacade drawDateFacade = new DrawDateConfiguration().createForTest(clock, drawDateRepository);
        drawDateFacade.getNextDrawDate();
        clock.plusMinutes(1);
        //when
        DrawDateDto drawDateDto = drawDateFacade.getPreviousDrawDate();
        //then
        LocalDateTime expectedDrawDate = saturdayBeforeNoon
                .withHour(12)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        assertThat(drawDateDto.time()).isEqualTo(expectedDrawDate);
    }

    @Test
    void should_return_expected_next_draw_date_for_particular_date() {
        //given
        LocalDateTime fridayAtNoon = LocalDateTime.now()
                .with(TemporalAdjusters.previous(DayOfWeek.FRIDAY))
                .withHour(12)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        Instant fridayNoonInstant = fridayAtNoon.toInstant(ZoneOffset.UTC);
        Clock clock = Clock.fixed(fridayNoonInstant, ZoneId.of("UTC"));
        DrawDateFacade drawDateFacade = new DrawDateConfiguration().createForTest(clock, drawDateRepository);
        //when
        DrawDateDto drawDateDto = drawDateFacade.getNextDrawDate(fridayAtNoon);
        //then
        LocalDateTime expectedDrawDate = fridayAtNoon
                .with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        assertThat(drawDateDto.time()).isEqualTo(expectedDrawDate);
    }

    @Test
    void should_return_expected_previous_draw_date_for_particular_date() {
        //given
        LocalDateTime fridayAtNoon = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.FRIDAY))
                .withHour(12)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        Instant fridayNoonInstant = fridayAtNoon.toInstant(ZoneOffset.UTC);
        AdjustableClock clock = new AdjustableClock(fridayNoonInstant, ZoneId.of("UTC"));
        DrawDateFacade drawDateFacade = new DrawDateConfiguration().createForTest(clock, drawDateRepository);
        drawDateFacade.getNextDrawDate(); // saturday 7
        clock.plusDays(7);
        drawDateFacade.getNextDrawDate(); // saturday 14
        //when
        LocalDateTime nextFridayAtNoon = fridayAtNoon.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        DrawDateDto drawDateDto = drawDateFacade.getPreviousDrawDate(nextFridayAtNoon);
        //then
        LocalDateTime expectedDrawDate = fridayAtNoon
                .with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        assertThat(drawDateDto.time()).isEqualTo(expectedDrawDate);
    }

    @Test
    void should_throw_exception_when_there_are_no_previous_draw_dates() {
        //given
        LocalDateTime saturdayBeforeNoon = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
                .withHour(11)
                .withMinute(59)
                .withSecond(59)
                .withNano(0);
        Instant fridayNoonInstant = saturdayBeforeNoon.toInstant(ZoneOffset.UTC);
        AdjustableClock clock = new AdjustableClock(fridayNoonInstant, ZoneId.of("UTC"));
        DrawDateFacade drawDateFacade = new DrawDateConfiguration().createForTest(clock, drawDateRepository);
        //when and then
        assertThrows(NoSuchElementException.class, drawDateFacade::getPreviousDrawDate);
    }

    @Test
    void should_return_next_draw_date_for_particular_time_if_draw_date_was_already_generated() {
        //given
        LocalDateTime thursdayAtNoon = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.THURSDAY))
                .withHour(12)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        Instant fridayNoonInstant = thursdayAtNoon.toInstant(ZoneOffset.UTC);
        AdjustableClock clock = new AdjustableClock(fridayNoonInstant, ZoneId.of("UTC"));
        DrawDateFacade drawDateFacade = new DrawDateConfiguration().createForTest(clock, drawDateRepository);
        //when
        drawDateFacade.getNextDrawDate();
        clock.plusDays(1);
        DrawDateDto drawDateDto = drawDateFacade.getNextDrawDate();
        //then
        LocalDateTime expectedDrawDate = thursdayAtNoon
                .with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        assertThat(drawDateDto.time()).isEqualTo(expectedDrawDate);
    }

    @Test
    void should_return_all_draw_dates() {
        //given
        LocalDateTime fridayAtNoon = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.FRIDAY))
                .withHour(12)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        Instant fridayNoonInstant = fridayAtNoon.toInstant(ZoneOffset.UTC);
        AdjustableClock clock = new AdjustableClock(fridayNoonInstant, ZoneId.of("UTC"));
        DrawDateFacade drawDateFacade = new DrawDateConfiguration().createForTest(clock, drawDateRepository);
        //when
        simulateAWeekPassing(drawDateFacade, clock);
        simulateAWeekPassing(drawDateFacade, clock);
        simulateAWeekPassing(drawDateFacade, clock);
        List<DrawDateDto> drawDates = drawDateFacade.retrieveAllDrawDates();
        //then
        LocalDateTime firstExpectedDrawDate = getNextSaturday(fridayAtNoon);
        LocalDateTime secondExpectedDrawDate = getNextSaturday(firstExpectedDrawDate);
        LocalDateTime thirdExpectedDrawDate = getNextSaturday(secondExpectedDrawDate);

        List<LocalDateTime> expectedDrawDates = List.of(firstExpectedDrawDate, secondExpectedDrawDate, thirdExpectedDrawDate);
        List<LocalDateTime> actualDrawDates = drawDates.stream()
                .map(DrawDateMapper::timeFromDrawDateDto)
                        .toList();
        assertThat(actualDrawDates).containsExactlyInAnyOrderElementsOf(expectedDrawDates);
    }

    private void simulateAWeekPassing(DrawDateFacade drawDateFacade, AdjustableClock clock) {
        drawDateFacade.getNextDrawDate();
        clock.plusDays(7);
    }

    private LocalDateTime getNextSaturday(LocalDateTime current) {
        return current.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
    }
}