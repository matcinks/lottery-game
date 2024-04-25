package org.mateusz.domain.drawdate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Configuration
public class DrawDateConfiguration {

    @Bean
    DrawDateRepository drawDateRepository() {
        return new DrawDateRepository() {
            @Override
            public DrawDate save(DrawDate drawDate) {
                return DrawDate.builder()
                        .id("001")
                        .time(LocalDateTime.now()
                                .with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
                                .withHour(12)
                                .withMinute(0)
                                .withSecond(0)
                                .withNano(0))
                        .build();
            }

            @Override
            public List<DrawDate> findAll() {
                return null;
            }

            @Override
            public Optional<DrawDate> findByDate(LocalDateTime time) {
                return Optional.empty();
            }

            @Override
            public Optional<BigInteger> findLastNumber() {
                return Optional.empty();
            }
        };
    }

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
