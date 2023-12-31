package org.mateusz.drawdate;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.Clock;

@AllArgsConstructor
public class DrawDateFacade {

    private final Clock clock;
    private final DrawDateGenerator drawDateGenerator;

    public LocalDateTime getNextDrawDate() {
        LocalDateTime currentTime = LocalDateTime.now(clock);
        return drawDateGenerator.generate(currentTime);
    }

}
