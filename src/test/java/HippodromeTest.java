import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HippodromeTest {


    @Test
    void constructorHasNull() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<>()));

        Assertions.assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void constructorHasEmpty() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(Collections.emptyList()));

        Assertions.assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorses() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horseList.add(new Horse("" + i, i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horseList);

        Assertions.assertEquals(horseList, hippodrome.getHorses());
    }

    @Test
    void move() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horseList.add(mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horseList);

        hippodrome.move();

        for (Horse horse : horseList) {
            verify(horse).move();
        }
    }

    @Test
    void getWinner() {
        Horse horseQ = new Horse("q1", 1, 2.999999);
        Horse horseW = new Horse("q2", 1, 2.0);
        Horse horseE = new Horse("q3", 1, 3.0);
        Horse horseR = new Horse("q4", 1, 1.0);
        Hippodrome hippodrome = new Hippodrome(List.of(horseQ, horseW, horseE, horseR));

        Assertions.assertSame(horseE, hippodrome.getWinner());
    }
}
