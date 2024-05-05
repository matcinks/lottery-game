package org.mateusz.infrastructure.resultchecker.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mateusz.domain.numbergenerator.WinningNumbersGeneratorFacade;
import org.mateusz.domain.resultchecker.ResultCheckerFacade;
import org.mateusz.domain.resultchecker.dto.PlayersResultsDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Log4j2
public class ResultCheckerScheduler {

    private final ResultCheckerFacade resultCheckerFacade;
    private final WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;

    @Scheduled(cron = "${lotto.result-checker.lotteryRunOccurrence}")
    public PlayersResultsDto generateWinners() {
        log.info("ResultCheckerScheduler started ...");
        if (!winningNumbersGeneratorFacade.areWinningNumbersGeneratedByNextDrawDate()) {
            log.error("Winning numbers are not yet generated");
            throw new RuntimeException("Winning numbers are not yet generated");
        }
        PlayersResultsDto playersResultsDto = resultCheckerFacade.generateResults();
        log.info(playersResultsDto.playersResults());
        log.info(playersResultsDto.message());
        return playersResultsDto;
    }
}
