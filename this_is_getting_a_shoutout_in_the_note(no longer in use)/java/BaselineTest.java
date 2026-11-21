//this imports the test utils
import org.junit.jupiter.api.Test;

//here we import the list and date and time functionality of java
import java.time.LocalDateTime;
import java.util.List;

//here we import the assertions util of java
import static org.junit.jupiter.api.Assertions.*;

//here we setup the main body of otu program
public class BaselineTest {

    //here we store all the event results to be called in our tests
    private Performance event(String id, String title, int hour, int minute,
                              int duration, int audience, String venue) {
        return new Performance(id, title,
                LocalDateTime.of(2026, 7, 18, hour, minute),
                duration, audience, venue, "Test Artist");
    }

    //here we test wehter our tree can and and find an event
    @Test
    void treeCanAddAndFindAnEvent() {
        EventTree tree = new EventTree();
        FestivalEvent e = event("T1", "Test", 12, 0, 30, 100, "Stage A");
        tree.add(e);
        EventTree.SearchResult result = tree.find(e.getStartTime(), e.getTitle());
        assertSame(e, result.event());
        // The node count is checked by your own Task 2c tests, not here.
    }

    //here we test if the breadth first search can find the shortest path in graph A
    @Test
    void breadthFirstSearchFindsFewestEdgeRouteInGraphA() {
        SearchResult result = GraphData.graphA().breadthFirstSearch("Entrance", "Acoustic Stage");
        assertTrue(result.found());
        assertEquals(3, result.routeLengthInEdges());
    }

    //here we test if there will be clashes if two events are at different venues
    @Test
    void noClashWhenEventsAreAtDifferentVenues() {
        ScheduleChecker checker = new ScheduleChecker();
        FestivalEvent existing = event("A", "Existing", 12, 30, 30, 100, "Stage A");
        FestivalEvent candidate = event("B", "Candidate", 12, 30, 30, 100, "Stage B");
        assertFalse(checker.hasClash(candidate, List.of(existing)));
    }

    //test if there will be clashes when two events take place at the same venue and time
    @Test
    void detectsCandidateStartingDuringExistingEvent() {
        ScheduleChecker checker = new ScheduleChecker();
        FestivalEvent existing = event("A", "Existing", 12, 0, 60, 100, "Stage A");
        FestivalEvent candidate = event("B", "Candidate", 12, 30, 30, 100, "Stage A");
        assertTrue(checker.hasClash(candidate, List.of(existing)));
    }

    //this is supposed to be bugged and for me to diagnose, but im ages away from that so i wont both
    //reading too much into it
    //the difference betweeb existinf and candidate do be its timing, existing is a 12:30 and lasts 30 min
    //while candidate is at 12:00 and lasts 120 minutes
    //i may not have used the debugger but i think the issue here has to do with too ling duration
    //like it goes over the existing event, but i also havent reasd enough of the rest of
    //the code to get why this is the case, anyway
    @Test
    void task3BugDemo_candidateSpansExistingEvent() {
        ScheduleChecker checker = new ScheduleChecker();
        FestivalEvent existing = event("A", "Existing", 12, 30, 30, 100, "Stage A");
        FestivalEvent candidate = event("B", "Candidate", 12, 0, 120, 100, "Stage A");

        // This test is expected to FAIL in the starter project.
        // Task 3 asks you to diagnose the reason using IntelliJ's debugger.
        //wonderful aint it
        assertTrue(checker.hasClash(candidate, List.of(existing)));
    }
}
