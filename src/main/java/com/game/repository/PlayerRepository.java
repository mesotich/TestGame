package com.game.repository;

import com.game.model.Player;

import java.util.List;

public interface PlayerRepository {

    List<Player> allPlayers();

    void add(Player player);

    Player get(int id);

    void edit(Player player);

    void remove(Player player);
}
