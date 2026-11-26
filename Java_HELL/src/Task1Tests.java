//import all required utilities
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

//the point of this file is to test whether my fix for task 1 is actually working

public class Task1Tests {

    //making sure og functionality is actually still, well, functioning, for performance specifically
    @Test
    void performanceTestPreservesExistingBehaviour() {
        LocalDateTime start =
                LocalDateTime.of(2026, 7, 18, 12, 0);

        Performance p = new Performance("P1", "Concert", start, 60, 200, "Main Stage", "Artist A");

        assertEquals("P1", p.getId());
        assertEquals("Concert", p.getTitle());
        assertEquals(start, p.getStartTime());
        assertEquals(60, p.getDurationMinutes());
        assertEquals(200, p.getExpectedAudience());
        assertEquals("Main Stage", p.getVenue());
        assertEquals("Artist A", p.getArtist());
        assertEquals(start.plusMinutes(60), p.getEndTime());
        assertEquals("12:00 Concert performance with Artist A, Main Stage", p.toString());
    }

    //making sure og functionality is actually still, well, functioning, for workshop specifically
    @Test
    void workshopPreservesExistingBehaviour() {
        LocalDateTime start =
                LocalDateTime.of(2026, 7, 18, 12, 0);

        Workshop w = new Workshop(
                "W1", "Java Workshop", start,
                90, 30, "Learning Tent", "Facilitator A"
        );

        assertEquals("W1", w.getId());
        assertEquals("Java Workshop", w.getTitle());
        assertEquals(start, w.getStartTime());
        assertEquals(90, w.getDurationMinutes());
        assertEquals(30, w.getExpectedAudience());
        assertEquals("Learning Tent", w.getVenue());
        assertEquals("Facilitator A", w.getFacilitator());
        assertEquals(start.plusMinutes(90), w.getEndTime());
        assertEquals(
                "12:00 Java Workshop workshop with Facilitator A, Learning Tent",
                w.toString()
        );
    }

    //here we further test that we didnt mess up any of the festival understanding
    @Test
    void bothRemainFestivalEvents() {
        LocalDateTime start =
                LocalDateTime.of(2026, 7, 18, 12, 0);

        FestivalEvent p = new Performance(
                "P1", "Concert", start, 60, 200,
                "Main Stage", "Artist A"
        );

        FestivalEvent w = new Workshop(
                "W1", "Workshop", start, 90, 30,
                "Learning Tent", "Facilitator A"
        );

        assertEquals("P1", p.getId());
        assertEquals("W1", w.getId());
    }


    //all three tests essentially show the previous functionality still working
}
