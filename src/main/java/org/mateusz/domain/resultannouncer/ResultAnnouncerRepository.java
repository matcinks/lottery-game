package org.mateusz.domain.resultannouncer;

import java.util.Optional;

public interface ResultAnnouncerRepository {

    Result save(Result result);

    Optional<Result> findById(String id);
}