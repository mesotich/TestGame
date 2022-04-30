package myTests;

import com.game.repository.ArrayListPlayerRepository;
import org.junit.Assert;
import org.junit.Test;

public class MyTest {

    @Test
    public void allPlayers() {
        ArrayListPlayerRepository arrayListPlayerRepository = new ArrayListPlayerRepository();
        String actual = arrayListPlayerRepository.allPlayers().get(0).getName();
        System.out.println(actual);
        String expected = "Ниус";
        Assert.assertEquals(expected, actual);
    }
}
