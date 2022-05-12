package com.game.service.filter;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class PlayerFilter implements FilterInterface<Player> {

    private final List<Predicate<Player>> filterList;

    public PlayerFilter(String name,
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
        filterList = new ArrayList<>();
        if (name != null)
            addFilter(player -> player.getName()
                    .contains(name.toLowerCase()));
        if (title != null)
            addFilter(player -> player.getTitle().toLowerCase()
                    .contains(title.toLowerCase()));
        if (race != null)
            addFilter(player -> player.getRace().equals(race));
        if (profession != null)
            addFilter(player -> player.getProfession().equals(profession));
        if (after != null)
            addFilter(player -> player.getBirthday().after(new Date(after)));
        if (before != null)
            addFilter(player -> player.getBirthday().before(new Date(before)));
        if (banned != null)
            addFilter(player -> player.isBanned() == banned);
        if (minExperience != null)
            addFilter(player -> player.getExperience() >= minExperience);
        if (maxExperience != null)
            addFilter(player -> player.getExperience() <= maxExperience);
        if (minLevel != null)
            addFilter(player -> player.getLevel() >= minLevel);
        if (maxLevel != null)
            addFilter(player -> player.getLevel() <= maxLevel);
    }

    public void addFilter(Predicate<Player> filter) {
        filterList.add(filter);
    }

    public List<Predicate<Player>> getFilterList() {
        return filterList;
    }
}
