package org.mateusz.domain.drawdate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class DrawDateConfiguration {

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public DrawDateFacade drawDateFacade(Clock clock, DrawDateRepository drawDateRepository) {
        DrawDateCalculator drawDateCalculator = new DrawDateCalculator();
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator();
        return new DrawDateFacade(clock, drawDateCalculator, drawDateGenerator, drawDateRepository);
    }

    public DrawDateFacade createForTest(Clock clock, DrawDateRepository drawDateRepository) {
        return drawDateFacade(clock, drawDateRepository);
    }
}
