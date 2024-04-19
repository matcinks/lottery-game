package org.mateusz.numbergenerator;

import org.mateusz.domain.numbergenerator.WinningNumbers;
import org.mateusz.domain.numbergenerator.WinningNumbersRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryWinningNumbersRepositoryTestImpl implements WinningNumbersRepository {

    Map<String, WinningNumbers> inMemoryDatabase = new ConcurrentHashMap<>();

    @Override
    public WinningNumbers save(WinningNumbers winningNumbers) {
        inMemoryDatabase.put(winningNumbers.id(), winningNumbers);
        return winningNumbers;
    }
}
