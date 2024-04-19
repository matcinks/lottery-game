package org.mateusz.resultannouncer;

import org.mateusz.resultchecker.ResultCheckerFacade;

public class ResultAnnouncerConfiguration {

    public ResultAnnouncerFacade createForTest(ResultAnnouncerRepository repository, ResultCheckerFacade resultCheckerFacade) {
        ResultProcessor resultProcessor = new ResultProcessor();
        return new ResultAnnouncerFacade(repository, resultCheckerFacade, resultProcessor);
    }
}