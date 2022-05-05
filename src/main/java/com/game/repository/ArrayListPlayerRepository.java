package com.game.repository;

import com.game.model.Player;
import com.game.repository.DAO.PlayerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArrayListPlayerRepository implements PlayerRepository {

    private List<Player> players;
    private PlayerDAO playerDAO;

    @Override
    public List<Player> allPlayers() {
        if (players == null) {
            players = playerDAO.readAllPlayers();
        }
        return players;
    }

    @Override
    public void add(Player player) {
        playerDAO.create(player);
        if (players == null)
            return;
        players.add(player);
    }

    @Override
    public Player get(int id) {
        if (players == null)
            players = playerDAO.readAllPlayers();
        return players.stream()
                .filter(pl -> pl.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void edit(Player editedPlayer) {
        playerDAO.update(editedPlayer);
        if (players == null)
            return;
        Player player = players.stream()
                .filter(p -> p.getId() == editedPlayer.getId())
                .findFirst()
                .orElse(null);
        if (player != null) {
            player.setName(editedPlayer.getName());
            player.setTitle(editedPlayer.getTitle());
            player.setRace(editedPlayer.getRace());
            player.setBirthday(editedPlayer.getBirthday());
            player.setBanned(editedPlayer.isBanned());
            player.setExperience(editedPlayer.getExperience());
            player.setLevel(editedPlayer.getLevel());
            player.setUntilNextLevel(editedPlayer.getUntilNextLevel());
        }
    }

    @Override
    public void remove(Player player) {
        playerDAO.delete(player);
        if (players == null)
            return;
        players.removeIf(p -> p.getId() == player.getId());
    }

    @Autowired
    public void setPlayerDAO(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }
}
