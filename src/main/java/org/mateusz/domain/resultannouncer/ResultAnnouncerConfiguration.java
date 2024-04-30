package org.mateusz.domain.resultannouncer;

import org.mateusz.domain.resultchecker.ResultCheckerFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResultAnnouncerConfiguration {

    @Bean
    public ResultAnnouncerFacade resultAnnouncerFacade(ResultAnnouncerRepository repository, ResultCheckerFacade resultCheckerFacade) {
        ResultProcessor resultProcessor = new ResultProcessor();
        return new ResultAnnouncerFacade(repository, resultCheckerFacade, resultProcessor);
    }
}