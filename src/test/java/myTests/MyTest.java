//Удалить
package myTests;

import com.game.config.MyConfig;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.model.Player;
import com.game.repository.ArrayListPlayerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;

public class MyTest {

    private ArrayListPlayerRepository repository = getRepository();
    private int currentIndex = 50;

    @Test
    public void testAll() {
        //allPlayers();
        add();
        get();
        edit();
        remove();
    }

@Test
    public void allPlayers() {
        int actual = repository.allPlayers().size();
        System.out.println(actual);
        int expected = 40;
        Assert.assertEquals(expected, actual);
    }

@Test
    public void add() {
        Player player = new Player("Иван", "Иванов", Race.ELF, Profession.WARRIOR, new Date(), false, 123, 12, 1567);
        repository.add(player);
        int actual = repository.allPlayers().size();
        int expected = 41;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get() {
        Player player = repository.get(currentIndex);
        String actual = player.getName();
        String expected = "Иван";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void edit() {
        Player player = repository.get(currentIndex);
        player.setBanned(true);
        repository.edit(player);
        player = repository.get(currentIndex);
        boolean actual = player.isBanned();
        boolean expected = true;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void remove() {
        Player player = repository.get(currentIndex);
        repository.remove(player);
        int actual = repository.allPlayers().size();
        int expected = 40;
        Assert.assertEquals(expected, actual);
    }

    private ArrayListPlayerRepository getRepository() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        return context.getBean("arrayListPlayerRepository", ArrayListPlayerRepository.class);
    }
}
