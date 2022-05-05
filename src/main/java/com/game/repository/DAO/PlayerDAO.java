package com.game.repository.DAO;

import com.game.model.Player;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PlayerDAO {

    void create(Player player);

    void update(Player updatedPlayer);

    void delete(Player player);

    List<Player> readAllPlayers();
}
