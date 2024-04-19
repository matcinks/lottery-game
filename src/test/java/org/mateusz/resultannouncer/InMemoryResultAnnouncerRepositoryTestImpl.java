package org.mateusz.resultannouncer;

import org.mateusz.domain.resultannouncer.Result;
import org.mateusz.domain.resultannouncer.ResultAnnouncerRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryResultAnnouncerRepositoryTestImpl implements ResultAnnouncerRepository {

    private final Map<String, Result> inMemoryDatabase = new ConcurrentHashMap<>();

    @Override
    public Result save(Result result) {
        inMemoryDatabase.put(result.id(), result);
        return result;
    }

    @Override
    public Optional<Result> findById(String id) {
        return Optional.ofNullable(inMemoryDatabase.get(id));
    }
}
