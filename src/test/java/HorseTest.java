import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    @Test
    void constructorHasNameIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
    }

    @Test
    void constructorHasNameIsNullWriteMessage() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 1, 1));

        Assertions.assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "\n", "\t", "\f", "\r"})
    void constructorHasNameIsWhiteSpace(String name) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Horse(name, 1, 1));

        Assertions.assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10, -100})
    void constructorHasNegativeSpeed(int speed) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Horse("name", speed, 1));

        Assertions.assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -3, -5, -10})
    void constructorHasNegativeDistance(int distance) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Horse("name", 1, distance));

        Assertions.assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getName() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Flash", 1, 1);

        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        String valueName = (String) name.get(horse);

        Assertions.assertEquals("Flash", valueName);
    }

    @Test
    void getSpeed() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Flash", 10, 1);

        Field speed = Horse.class.getDeclaredField("speed");
        speed.setAccessible(true);
        int valueSpeed = (int) speed.get(horse);

        Assertions.assertEquals(10, valueSpeed);
    }

    @Test
    void getDistance() throws NoSuchFieldException, IllegalAccessException {
        Horse horseThree = new Horse("Flash", 1, 10);

        Field distance = Horse.class.getDeclaredField("distance");
        distance.setAccessible(true);
        double valueDistance = (double) distance.get(horseThree);

        Assertions.assertEquals(10, valueDistance);
    }

    @Test
    void getDistanceZero() {
        Horse horse = new Horse("Flash", 1);

        Assertions.assertEquals(0, horse.getDistance());
    }

    @Test
    void move() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("Flash", 21, 321);

            horse.move();

            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.0, 0.1, 0.2, 0.0, 0.9, 999, 999, 0.5})
    void GetRandomDouble(double random) {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("Flash", 21, 321);
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            horse.move();

            Assertions.assertEquals(321 + 21 * random, horse.getDistance());
        }
    }
}
