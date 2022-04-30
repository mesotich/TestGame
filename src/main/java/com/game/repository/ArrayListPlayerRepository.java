package com.game.repository;

import com.game.model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArrayListPlayerRepository implements PlayerRepository {

    private List<Player> players = new ArrayList<>();

    @Override
    public List<Player> allPlayers() {
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Player.class)
                .buildSessionFactory();
        Session session = null;
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            Player player = session.get(Player.class,1);
            players.add(player);
            System.out.println(player);
            session.getTransaction().commit();
            System.out.println("Done");
        } finally {
            session.close();
            factory.close();
        }
        return players;
    }

    @Override
    public void add(Player player) {
        players.add(player);
    }

    @Override
    public Player get(int id) {
        return players.get(id);
    }

    @Override
    public void edit(Player editedPlayer) {
        players.get(editedPlayer.getId());
    }

    @Override
    public void remove(Player player) {
    }
}
