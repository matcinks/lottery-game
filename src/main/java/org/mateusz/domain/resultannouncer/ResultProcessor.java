package org.mateusz.domain.resultannouncer;

import java.time.LocalDateTime;

class ResultProcessor {

    public String process(Result result) {
        LocalDateTime ticketDrawDate = result.drawDate();
        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isBefore(ticketDrawDate)) {
            return String.format(ResultMessages.WAITING_MESSAGE, calculateRemainingDays(ticketDrawDate, currentTime));
        }
        if (result.isWinner()) {
            return ResultMessages.WIN;
        }
        return ResultMessages.LOSE;
    }

    private int calculateRemainingDays(LocalDateTime fromTime, LocalDateTime toTime) {
        return fromTime.getDayOfYear() - toTime.getDayOfYear();
    }
}