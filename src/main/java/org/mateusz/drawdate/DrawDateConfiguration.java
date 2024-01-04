package org.mateusz.drawdate;

import java.time.Clock;

public class DrawDateConfiguration {

    public DrawDateFacade createForTest(Clock clock) {
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator();
        return new DrawDateFacade(clock, drawDateGenerator);
    }

}
