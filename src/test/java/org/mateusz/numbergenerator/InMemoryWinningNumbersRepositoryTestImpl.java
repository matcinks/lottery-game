package org.mateusz.numbergenerator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryWinningNumbersRepositoryTestImpl implements WinningNumbersRepository{

    Map<String, WinningNumbers> inMemoryDatabase = new ConcurrentHashMap<>();

    @Override
    public WinningNumbers save(WinningNumbers winningNumbers) {
        inMemoryDatabase.put(winningNumbers.id(), winningNumbers);
        return winningNumbers;
    }
}
