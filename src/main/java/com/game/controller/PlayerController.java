package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class PlayerController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping("/players")
    public List<Player> getSortedPlayerList(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) String title,
                                            @RequestParam(required = false) Race race,
                                            @RequestParam(required = false) Profession profession,
                                            @RequestParam(required = false) Long after,
                                            @RequestParam(required = false) Long before,
                                            @RequestParam(required = false) Boolean banned,
                                            @RequestParam(required = false) Integer minExperience,
                                            @RequestParam(required = false) Integer maxExperience,
                                            @RequestParam(required = false) Integer minLevel,
                                            @RequestParam(required = false) Integer maxLevel,
                                            @RequestParam(required = false) String order,
                                            @RequestParam(required = false) Integer pageNumber,
                                            @RequestParam(required = false) Integer pageSize
    ) {
        List<Player> filteredList = playerService.getFilterPlayersList(name, title, race, profession, after, before, banned, minExperience,
                maxExperience, minLevel, maxLevel);
        return playerService.getSortedPlayerList(filteredList, pageNumber, pageSize, order);
    }

    @GetMapping("/players/count")
    public Integer count(@RequestParam(required = false) String name,
                         @RequestParam(required = false) String title,
                         @RequestParam(required = false) Race race,
                         @RequestParam(required = false) Profession profession,
                         @RequestParam(required = false) Long after,
                         @RequestParam(required = false) Long before,
                         @RequestParam(required = false) Boolean banned,
                         @RequestParam(required = false) Integer minExperience,
                         @RequestParam(required = false) Integer maxExperience,
                         @RequestParam(required = false) Integer minLevel,
                         @RequestParam(required = false) Integer maxLevel
    ) {
        return playerService.getFilterPlayersList(name, title, race, profession, after, before, banned, minExperience,
                maxExperience, minLevel, maxLevel).size();
    }

    @GetMapping("/players/{id}")
    public Player getPlayer(@PathVariable Long id) {
        return playerService.getPlayer(id);
    }

    @PostMapping("/players")
    public @ResponseBody
    Player createPlayer(@RequestBody Player jsonBody) {
        Player player = playerService.createPlayer(jsonBody);
        playerRepository.save(player);
        return player;
    }

    @PostMapping("/players/{id}")
    public @ResponseBody
    Player updatePlayer(@RequestBody Player jsonBody, @PathVariable Long id) {
        Player playerToUpdate = playerService.getPlayer(id);
        if (jsonBody.equals(new Player()))
            return playerToUpdate;
        if (playerService.updatePlayer(playerToUpdate, jsonBody)) {
            playerRepository.save(playerToUpdate);
        }
        return playerToUpdate;
    }

    @DeleteMapping("/players/{id}")
    public void deletePlayer(@PathVariable Long id) {
        playerRepository.delete(getPlayer(id));
    }
}
