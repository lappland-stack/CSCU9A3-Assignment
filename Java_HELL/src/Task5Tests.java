//imports all required utils
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class Task5Tests {

    private FestivalEvent event(String id, String title, int hour, int audience) {
        return new Performance(id, title, LocalDateTime.of(2026, 7, 18, hour, 0), 30, audience, "Main Stage", "Artist");

    }
        @Test
        void sortsByStartTime() {
            List<FestivalEvent> events = new ArrayList<>(List.of(
                    event("1", "Later", 12, 100),
                    event("2", "Earlier", 10, 100)
            ));

            FestivalSorter.quickSort(events);

            assertEquals("Earlier", events.get(0).getTitle());
            assertEquals("Later", events.get(1).getTitle());
        }

        @Test
        void largerAudienceComesFirstWhenTimesMatch() {
            List<FestivalEvent> events = new ArrayList<>(List.of(
                    event("1", "Smaller", 10, 100),
                    event("2", "Larger", 10, 200)
            ));

            FestivalSorter.quickSort(events);

            assertEquals("Larger", events.get(0).getTitle());
            assertEquals("Smaller", events.get(1).getTitle());
        }

        @Test
        void titlesAreComparedIgnoringCaseWhenOtherRulesTie() {
            List<FestivalEvent> events = new ArrayList<>(List.of(
                    event("1", "banana", 10, 100),
                    event("2", "Apple", 10, 100),
                    event("3", "Cherry", 10, 100)
            ));

            FestivalSorter.quickSort(events);

            assertEquals(
                    List.of("Apple", "banana", "Cherry"),
                    events.stream().map(FestivalEvent::getTitle).toList()
            );
        }

        @Test
        void appliesAllThreeOrderingRulesTogether() {
            List<FestivalEvent> events = new ArrayList<>(List.of(
                    event("1", "banana", 11, 100),
                    event("2", "Earlier", 10, 50),
                    event("3", "Apple", 11, 100),
                    event("4", "Largest Audience", 10, 200)
            ));

            FestivalSorter.quickSort(events);

            assertEquals(
                    List.of("Largest Audience", "Earlier", "Apple", "banana"),
                    events.stream().map(FestivalEvent::getTitle).toList()
            );
        }
    }
