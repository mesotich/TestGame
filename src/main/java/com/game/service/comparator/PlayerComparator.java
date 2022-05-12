package com.game.service.comparator;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;

import java.util.Comparator;

public class PlayerComparator implements ComparatorInterface<Player> {

    @Override
    public Comparator<Player> getComparator(String order) {
        Comparator<Player> comparator = null;
        switch (PlayerOrder.valueOf(order)) {
            case ID: {
                comparator = Comparator.comparing(Player::getId);
                break;
            }
            case NAME: {
                comparator = Comparator.comparing(Player::getName);
                break;
            }
            case EXPERIENCE: {
                comparator = Comparator.comparing(Player::getExperience);
                break;
            }
            case BIRTHDAY: {
                comparator = Comparator.comparing(Player::getBirthday);
                break;
            }
            case LEVEL: {
                comparator = Comparator.comparing(Player::getLevel);
            }
        }
        return comparator;
    }
}

