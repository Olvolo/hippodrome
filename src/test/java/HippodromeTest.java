import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HippodromeTest {

    @Test
    public void testConstructor_NullHorses_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void testConstructor_NullHorses_ExceptionMessageContainsCorrectMessage() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    public void testConstructor_EmptyHorses_ThrowsIllegalArgumentException() {
        List<Horse> emptyHorses = Collections.emptyList();
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(emptyHorses));
    }

    @Test
    public void testConstructor_EmptyHorses_ExceptionMessageContainsCorrectMessage() {
        List<Horse> emptyHorses = Collections.emptyList();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(emptyHorses));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    public void testGetHorses_ReturnsSameListWithSameOrder() {
        List<Horse> expectedHorses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Horse horse = new Horse("Horse " + i, 10.0);
            expectedHorses.add(horse);
        }
        Hippodrome hippodrome = new Hippodrome(expectedHorses);
        List<Horse> actualHorses = hippodrome.getHorses();
        assertIterableEquals(expectedHorses, actualHorses);
    }

    @Test
    public void testMove_CallsMoveMethodForAllHorses() {
        List<Horse> mockHorses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Horse mockHorse = mock(Horse.class);
            mockHorses.add(mockHorse);
        }

        Hippodrome hippodrome = new Hippodrome(mockHorses);
        hippodrome.move();

        for (Horse mockHorse : mockHorses) {
            verify(mockHorse).move();
        }
    }


    @Test
    public void testGetWinner_ReturnsHorseWithHighestDistance() {
        Horse horse1 = new Horse("Horse 1", 10.0, 100.0);
        Horse horse2 = new Horse("Horse 2", 12.0, 150.0);
        Horse horse3 = new Horse("Horse 3", 8.0, 120.0);
        List<Horse> horses = new ArrayList<>();
        horses.add(horse1);
        horses.add(horse2);
        horses.add(horse3);
        Hippodrome hippodrome = new Hippodrome(horses);

        Horse winner = hippodrome.getWinner();

        assertEquals(horse2, winner);
    }
}
