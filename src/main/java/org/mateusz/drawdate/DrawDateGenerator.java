package org.mateusz.drawdate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

class DrawDateGenerator {
    private static final LocalTime DRAW_TIME = LocalTime.NOON;
    private static final DayOfWeek DRAW_DAY = DayOfWeek.SATURDAY;

    LocalDateTime generate(LocalDateTime currentTime) {
        if (isSaturdayAndBeforeNoon(currentTime)) {
            return LocalDateTime.of(currentTime.toLocalDate(), DRAW_TIME);
        }
        return LocalDateTime.of(nextDrawDate(currentTime), DRAW_TIME);
    }

    private LocalDate nextDrawDate(LocalDateTime currentTime) {
        TemporalAdjuster nextDrawDateAdjuster = TemporalAdjusters.next(DRAW_DAY);
        return LocalDate.from(currentTime.with(nextDrawDateAdjuster));
    }

    private boolean isSaturdayAndBeforeNoon(LocalDateTime currentTime) {
        return currentTime.getDayOfWeek().equals(DRAW_DAY) &&
                currentTime.toLocalTime().isBefore(DRAW_TIME);
    }

}