package org.mateusz.domain.drawdate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

class DrawDateCalculator {
    private static final DayOfWeek DRAW_DAY = DayOfWeek.SATURDAY;
    private static final LocalTime DRAW_TIME = LocalTime.NOON;

    LocalDateTime calculateNextDrawDate(LocalDateTime currentTime) {
        if (isSaturdayAndBeforeNoon(currentTime)) {
            return LocalDateTime.of(currentTime.toLocalDate(), DRAW_TIME);
        }
        return LocalDateTime.of(nextDrawDate(currentTime), DRAW_TIME);
    }

    LocalDateTime calculatePreviousDrawDate(LocalDateTime currentTime) {
        if (isSaturdayAndAfterNoon(currentTime)) {
            return LocalDateTime.of(currentTime.toLocalDate(), DRAW_TIME);
        }
        return LocalDateTime.of(previousDrawDate(currentTime), DRAW_TIME);
    }

    private boolean isSaturdayAndBeforeNoon(LocalDateTime currentTime) {
        return currentTime.getDayOfWeek().equals(DRAW_DAY) &&
                currentTime.toLocalTime().isBefore(DRAW_TIME);
    }

    private LocalDate nextDrawDate(LocalDateTime currentTime) {
        TemporalAdjuster nextDrawDateAdjuster = TemporalAdjusters.next(DRAW_DAY);
        return LocalDate.from(currentTime.with(nextDrawDateAdjuster));
    }

    private boolean isSaturdayAndAfterNoon(LocalDateTime currentTime) {
        return currentTime.getDayOfWeek().equals(DRAW_DAY) &&
                currentTime.toLocalTime().isAfter(DRAW_TIME);
    }

    private LocalDate previousDrawDate(LocalDateTime currentTime) {
        TemporalAdjuster previousDrawDateAdjuster = TemporalAdjusters.previous(DRAW_DAY);
        return LocalDate.from(currentTime.with(previousDrawDateAdjuster));
    }
}