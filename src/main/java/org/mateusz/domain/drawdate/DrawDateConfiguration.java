package org.mateusz.domain.drawdate;

import java.time.Clock;

public class DrawDateConfiguration {

    public DrawDateFacade createForTest(Clock clock, DrawDateRepository drawDateRepository) {
        DrawDateCalculator drawDateCalculator = new DrawDateCalculator();
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator();
        return new DrawDateFacade(clock, drawDateCalculator, drawDateGenerator, drawDateRepository);
    }
}
