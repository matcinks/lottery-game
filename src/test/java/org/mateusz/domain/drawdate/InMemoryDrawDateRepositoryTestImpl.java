package org.mateusz.domain.drawdate;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryDrawDateRepositoryTestImpl implements DrawDateRepository {

    Map<String, DrawDate> inMemoryDatabase = new ConcurrentHashMap<>();

    @Override
    public DrawDate save(DrawDate drawDate) {
        inMemoryDatabase.put(drawDate.id(), drawDate);
        return drawDate;
    }

    @Override
    public List<DrawDate> findAll() {
        return new ArrayList<>(inMemoryDatabase.values());
    }

    @Override
    public Optional<DrawDate> findByDate(LocalDateTime time) {
        return inMemoryDatabase.values()
                .stream()
                .filter(drawDate ->
                        drawDate.time()
                                .isEqual(time))
                .findFirst();
    }

    @Override
    public Optional<BigInteger> findLastNumber() {
        return inMemoryDatabase.values().stream()
                .map(DrawDate::number)
                .max(BigInteger::compareTo);
    }
}
