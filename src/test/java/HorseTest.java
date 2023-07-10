import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class HorseTest {

    @Test
    void constructor_nullName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 10.0, 100.0));
    }

    @Test
    void constructor_nullName_exceptionMessageContainsCorrectErrorMessage() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 10.0, 100.0));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    public void testConstructor_BlankName_ThrowsIllegalArgumentException(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, 10.0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    public void testConstructor_BlankName_ExceptionMessageContainsCorrectMessage(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 10.0));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void constructor_negativeSpeed_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Hydrocephalus", -10.0, 100.0));
    }

    @Test
    void constructor_negativeSpeed_exceptionMessageContainsCorrectErrorMessage() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Hydrocephalus", -10.0, 100.0));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void constructor_negativeDistance_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Hydrocephalus", 10.0, -100.0));
    }

    @Test
    void constructor_negativeDistance_exceptionMessageContainsCorrectErrorMessage() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Hydrocephalus", 10.0, -100.0));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    /** Этот тест был дан в разборе, до такого пришлось бы долго додумываться(ниже) */
    @Test
    public void getNameTest() throws NoSuchFieldException, IllegalAccessException {
    Horse horse = new Horse("Horse", 10, 100);
        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        String nameValue = (String) name.get(horse);
        assertEquals("Horse", nameValue);
                   }
    @Test
    public void getNameTestFromConstructorSimpleWay() {
        Horse horse = new Horse("Horse", 10, 100);
             assertEquals("Horse", horse.getName());

    }

    /** Чуть видоизменённо для второго конструктора */
    @Test
    public void testGetName_ReturnsCorrectName() {
        String expectedName = "Horse";
        Horse horse = new Horse(expectedName, 10.0);
        String actualName = horse.getName();
        assertEquals(expectedName, actualName);
    }
    @Test
    public void testGetSpeed_ReturnsCorrectSpeed() {
        double expectedSpeed = 10.0;
        Horse horse = new Horse("Horse", expectedSpeed);
        double actualSpeed = horse.getSpeed();
        assertEquals(expectedSpeed, actualSpeed);
    }

    @Test
    public void testGetDistance_ReturnsCorrectDistance() {
        double expectedDistance = 100.0;
        Horse horse = new Horse("Horse", 10.0, expectedDistance);
        double actualDistance = horse.getDistance();
        assertEquals(expectedDistance, actualDistance);
    }

    @Test
    public void testGetDistance_ReturnsZeroForConstructorWithTwoParameters() {
        double expectedDistance = 0.0;
        Horse horse = new Horse("Horse", 10.0);
        double actualDistance = horse.getDistance();
        assertEquals(expectedDistance, actualDistance);
    }

    @Test
    public void testMove_CallsGetRandomDoubleWithCorrectParameters() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            double expectedMin = 0.2;
            double expectedMax = 0.9;

            Horse horse = new Horse("Horse", 10.0, 100.0);
            horse.move();

            mockedStatic.verify(() -> Horse.getRandomDouble(expectedMin, expectedMax));
        }
    }


    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.5, 0.9, 1.0, 999.999, 0.0})
    void testMove_AssignsCorrectDistance(double random) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            double expectedMin = 0.2;
            double expectedMax = 0.9;
            double expectedSpeed = 10.0;
            double expectedRandomValue = random;
            double expectedDistance = 100.0 + expectedSpeed * expectedRandomValue;

            mockedStatic.when(() -> Horse.getRandomDouble(expectedMin, expectedMax))
                    .thenReturn(expectedRandomValue);

            Horse horse = new Horse("Horse", expectedSpeed, 100.0);
            horse.move();

            assertEquals(expectedDistance, horse.getDistance());
            mockedStatic.verify(() -> Horse.getRandomDouble(expectedMin, expectedMax));
        }
    }
}