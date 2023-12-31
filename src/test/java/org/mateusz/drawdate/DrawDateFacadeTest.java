package org.mateusz.drawdate;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.assertj.core.api.Assertions.assertThat;

class DrawDateFacadeTest {

    DrawDateGenerator drawDateGenerator = new DrawDateGenerator();

    @Test
    void should_return_draw_date_for_current_week_exactly_this_week_saturday_at_noon() {
        //given
        Clock fixedClock = Clock.fixed(ZonedDateTime.of(
                2023,
                12,
                30,
                11,
                59,
                59,
                0,
                ZoneId.of("GMT")
        ).toInstant(), ZoneId.of("GMT"));
        DrawDateFacade drawDateFacade = new DrawDateFacade(fixedClock, drawDateGenerator);
        //when
        LocalDateTime drawDate = drawDateFacade.getNextDrawDate();
        //then
        LocalDateTime currentWeekSaturdayAtNoon = LocalDateTime.of(2023, 12, 30, 12, 0, 0, 0 );
        assertThat(drawDate).isEqualTo(currentWeekSaturdayAtNoon);
    }

    @Test
    void should_return_draw_date_for_next_week_exactly_next_week_saturday_at_noon() {
        //given
        Clock fixedClock = Clock.fixed(ZonedDateTime.of(
                2023,
                12,
                30,
                12,
                0,
                1,
                0,
                ZoneId.of("GMT")
        ).toInstant(), ZoneId.of("GMT"));
        DrawDateFacade drawDateFacade = new DrawDateFacade(fixedClock, drawDateGenerator);
        //when
        LocalDateTime drawDate = drawDateFacade.getNextDrawDate();
        //then
        LocalDateTime nextWeekSaturdayAtNoon = LocalDateTime.of(2024, 1, 6, 12, 0, 0, 0 );
        assertThat(drawDate).isEqualTo(nextWeekSaturdayAtNoon);
    }

}