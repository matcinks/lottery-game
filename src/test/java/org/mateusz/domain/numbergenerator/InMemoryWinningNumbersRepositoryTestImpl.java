package org.mateusz.domain.numbergenerator;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryWinningNumbersRepositoryTestImpl implements WinningNumbersRepository {

    Map<String, WinningNumbers> inMemoryDatabase = new ConcurrentHashMap<>();

    @Override
    public WinningNumbers save(WinningNumbers winningNumbers) {
        inMemoryDatabase.put(winningNumbers.id(), winningNumbers);
        return winningNumbers;
    }

    @Override
    public Optional<WinningNumbers> findNumbersByDate(LocalDateTime date) {
        return inMemoryDatabase.values()
                .stream()
                .filter(numbers -> numbers.drawDate()
                        .isEqual(date))
                .findFirst();
    }
}
