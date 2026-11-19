import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BaselineTest {

    private Performance event(String id, String title, int hour, int minute,
                              int duration, int audience, String venue) {
        return new Performance(id, title,
                LocalDateTime.of(2026, 7, 18, hour, minute),
                duration, audience, venue, "Test Artist");
    }

    @Test
    void treeCanAddAndFindAnEvent() {
        EventTree tree = new EventTree();
        FestivalEvent e = event("T1", "Test", 12, 0, 30, 100, "Stage A");
        tree.add(e);
        EventTree.SearchResult result = tree.find(e.getStartTime(), e.getTitle());
        assertSame(e, result.event());
        // The node count is checked by your own Task 2c tests, not here.
    }

    @Test
    void breadthFirstSearchFindsFewestEdgeRouteInGraphA() {
        SearchResult result = GraphData.graphA().breadthFirstSearch("Entrance", "Acoustic Stage");
        assertTrue(result.found());
        assertEquals(3, result.routeLengthInEdges());
    }

    @Test
    void noClashWhenEventsAreAtDifferentVenues() {
        ScheduleChecker checker = new ScheduleChecker();
        FestivalEvent existing = event("A", "Existing", 12, 30, 30, 100, "Stage A");
        FestivalEvent candidate = event("B", "Candidate", 12, 30, 30, 100, "Stage B");
        assertFalse(checker.hasClash(candidate, List.of(existing)));
    }

    @Test
    void detectsCandidateStartingDuringExistingEvent() {
        ScheduleChecker checker = new ScheduleChecker();
        FestivalEvent existing = event("A", "Existing", 12, 0, 60, 100, "Stage A");
        FestivalEvent candidate = event("B", "Candidate", 12, 30, 30, 100, "Stage A");
        assertTrue(checker.hasClash(candidate, List.of(existing)));
    }

    @Test
    void task3BugDemo_candidateSpansExistingEvent() {
        ScheduleChecker checker = new ScheduleChecker();
        FestivalEvent existing = event("A", "Existing", 12, 30, 30, 100, "Stage A");
        FestivalEvent candidate = event("B", "Candidate", 12, 0, 120, 100, "Stage A");

        // This test is expected to FAIL in the starter project.
        // Task 3 asks you to diagnose the reason using IntelliJ's debugger.
        assertTrue(checker.hasClash(candidate, List.of(existing)));
    }
}
