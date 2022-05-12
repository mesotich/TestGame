package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.exception.BadRequestException;
import com.game.exception.NotFoundException;
import com.game.repository.PlayerRepository;
import com.game.service.comparator.ComparatorInterface;
import com.game.service.comparator.PlayerComparator;
import com.game.service.executor.CalculateQuery;
import com.game.service.executor.CheckQuery;
import com.game.service.executor.PlayerExecutor;
import com.game.service.filter.FilterInterface;
import com.game.service.filter.PlayerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Player createPlayer(Player player) {
        if (player == null) {
            return null;
        }
        Player newPlayer = new Player();
        if (player.isBanned() != null) {
            newPlayer.setBanned(player.isBanned());
        } else {
            newPlayer.setBanned(false);
        }
        PlayerExecutor playerExecutor = new PlayerExecutor(player);
        for (CheckQuery checkQuery : CheckQuery.values()
        ) {
            playerExecutor.executeCheck(checkQuery);
        }
        String name = player.getName();
        String title = player.getTitle();
        Race race = player.getRace();
        Profession profession = player.getProfession();
        Integer experience = player.getExperience();
        Date birthday = player.getBirthday();
        Integer level = playerExecutor.executeCalculate(CalculateQuery.LEVEL);
        Integer untilNextLevel = playerExecutor.executeCalculate(CalculateQuery.UNTIL_LEVEL);
        newPlayer.setName(name);
        newPlayer.setTitle(title);
        newPlayer.setRace(race);
        newPlayer.setProfession(profession);
        newPlayer.setExperience(experience);
        newPlayer.setBirthday(birthday);
        newPlayer.setLevel(level);
        newPlayer.setUntilNextLevel(untilNextLevel);
        return newPlayer;
    }

    public Player getPlayer(Long id) {
        if (id == 0) {
            throw new BadRequestException();
        }
        return playerRepository.findAll().stream()
                .filter(player -> player.getId().equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    public List<Player> getFilterPlayersList(String name,
                                             String title,
                                             Race race,
                                             Profession profession,
                                             Long after,
                                             Long before,
                                             Boolean banned,
                                             Integer minExperience,
                                             Integer maxExperience,
                                             Integer minLevel,
                                             Integer maxLevel) {
        List<Player> filteredPlayerList = playerRepository.findAll();
        FilterInterface<Player> filter = new PlayerFilter(name, title, race, profession,
                after, before,
                banned,
                minExperience, maxExperience,
                minLevel, maxLevel);
        for (Predicate<Player> predicate : filter.getFilterList())
            filteredPlayerList = filteredPlayerList.stream()
                    .filter(predicate)
                    .collect(Collectors.toList());
        return filteredPlayerList;
    }

    public List<Player> getSortedPlayerList(List<Player> playerList,
                                            Integer pageNumber,
                                            Integer pageSize,
                                            String order) {
        if (pageSize == null)
            pageSize = 3;
        pageNumber = pageNumber != null ? pageNumber * pageSize : 0;
        ComparatorInterface<Player> comparatorInterface = new PlayerComparator();
        Comparator<Player> comparator = order != null
                ? comparatorInterface.getComparator(order)
                : comparatorInterface.getComparator("ID");
        return playerList.stream()
                .skip(pageNumber)
                .limit(pageSize)
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public boolean updatePlayer(Player player, Player jsonBody) {
        if (player == null)
            return false;
        PlayerExecutor playerExecutor = new PlayerExecutor(jsonBody);
        if (jsonBody.getName() != null) {
            playerExecutor.executeCheck(CheckQuery.NAME_NOT_EMPTY);
            playerExecutor.executeCheck(CheckQuery.NAME_LENGTH);
            player.setName(jsonBody.getName());
        }
        if (jsonBody.getTitle() != null) {
            playerExecutor.executeCheck(CheckQuery.TITLE_NOT_EMPTY);
            playerExecutor.executeCheck(CheckQuery.TITLE_LENGTH);
            player.setTitle(jsonBody.getTitle());
        }
        if (jsonBody.getRace() != null) {
            player.setRace(jsonBody.getRace());
        }
        if (jsonBody.getProfession() != null) {
            player.setProfession(jsonBody.getProfession());
        }
        if (jsonBody.isBanned() != null) {
            player.setBanned(jsonBody.isBanned());
        } else {
            player.setBanned(false);
        }
        if (jsonBody.getExperience() != null) {
            playerExecutor.executeCheck(CheckQuery.EXPERIENCE_CORRECT);
            player.setExperience(jsonBody.getExperience());
            player.setLevel(playerExecutor.executeCalculate(CalculateQuery.LEVEL));
            player.setUntilNextLevel(playerExecutor.executeCalculate(CalculateQuery.UNTIL_LEVEL));
        }
        if (jsonBody.getBirthday() != null) {
            playerExecutor.executeCheck(CheckQuery.DATE_CORRECT);
            player.setBirthday(jsonBody.getBirthday());
        }
        return true;
    }
}
