package com.game.repository.DAO;

import com.game.model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerDAOImp implements PlayerDAO {

    private List<Player> players;

    @Override
    public void create(Player player) {
        executeOperation(player, CRUDOperation.CREATE);
    }

    @Override
    public void update(Player updatedPlayer) {
        executeOperation(updatedPlayer, CRUDOperation.UPDATE);
    }

    @Override
    public void delete(Player player) {
        executeOperation(player, CRUDOperation.DELETE);
    }

    @Override
    public List<Player> readAllPlayers() {
        executeOperation(null, CRUDOperation.READ_ALL);
        return players;
    }

    private void executeOperation(Player player, CRUDOperation crudOperation) {
        SessionFactory factory = getSessionFactory();
        Session session = null;
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            switch (crudOperation) {
                case CREATE:
                    session.save(player);
                    break;
                case READ_ALL:
                    players = session.createQuery("from Player").getResultList();
                    break;
                case UPDATE:
                    session.update(player);
                    break;
                case DELETE:
                    session.delete(player);
                    break;
            }
            session.getTransaction().commit();
            System.out.println("Done");
        } finally {
            session.close();
            factory.close();
        }
    }

    private SessionFactory getSessionFactory() {
        return new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Player.class)
                .buildSessionFactory();
    }
}
