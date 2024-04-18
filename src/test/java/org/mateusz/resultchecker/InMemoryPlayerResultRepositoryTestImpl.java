package org.mateusz.resultchecker;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryPlayerResultRepositoryTestImpl implements PlayerRepository{
    Map<String, Player> inMemoryDatabase = new ConcurrentHashMap();

    @Override
    public List<Player> saveAll(List<Player> players) {
        players.forEach(player -> inMemoryDatabase.put(player.id(), player));
        return players;
    }
    @Override
    public Optional<Player> findById(String id) {
        Player player = inMemoryDatabase.get(id);
        return Optional.ofNullable(player);
    }
}
