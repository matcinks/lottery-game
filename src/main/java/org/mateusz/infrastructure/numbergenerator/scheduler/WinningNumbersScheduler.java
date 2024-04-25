package org.mateusz.infrastructure.numbergenerator.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mateusz.domain.numbergenerator.WinningNumbersGeneratorFacade;
import org.mateusz.domain.numbergenerator.dto.WinningNumbersDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Log4j2
public class WinningNumbersScheduler {

    private final WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;

    @Scheduled(cron = "*/10 * * * * *")
    public void generateWinningNumbers() {
        log.info("Winning numbers scheduler enabled");
        WinningNumbersDto winningNumbersDto = winningNumbersGeneratorFacade.generateWinningNumbers();
        log.info(winningNumbersDto);
    }
}
