package org.mateusz.drawdate;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

class DrawDateGenerator {

    DrawDate generateDrawDate(LocalDateTime expectedNextDrawDate, BigInteger lastDrawDateNumber) {
        String drawDateId = UUID.randomUUID().toString();
        return DrawDate.builder()
                .id(drawDateId)
                .number(lastDrawDateNumber)
                .time(expectedNextDrawDate)
                .build();
    }
}