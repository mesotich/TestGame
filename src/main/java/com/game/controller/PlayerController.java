package com.game.controller;

import com.game.repository.ArrayListPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rest/players")
public class PlayerController {

    private final ArrayListPlayerRepository playerRepository;

    @Autowired
    public PlayerController(ArrayListPlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }
}
