//importing required utils
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

public class Task2Tests {

    private Performance makeEvent(String title, int hour) {
        return new Performance(title, title,
                LocalDateTime.of(2026, 6, 1, hour, 0),
                30,100,"Main Stage", "Artist");
    }
    @Test
    void emptyTreeHasHeightZero() {
        EventTree tree = new EventTree();

        assertEquals(0, tree.height());
    }

    @Test
    void singleNodeTreeHasHeightOne() {
        EventTree tree = new EventTree();
        tree.add(makeEvent("Event A", 10));

        assertEquals(1, tree.height());
    }

    @Test
    void treeWithTwoLevelsHasHeightTwo() {
        EventTree tree = new EventTree();

        tree.add(makeEvent("Event B", 13));
        tree.add(makeEvent("Event A", 10));
        tree.add(makeEvent("Event C", 17));

        assertEquals(2, tree.height());
    }

    @Test
    void chronologicalInsertionCreatesHeightFour() {
        EventTree tree = new EventTree();

        tree.add(makeEvent("Event A", 10));
        tree.add(makeEvent("Event B", 11));
        tree.add(makeEvent("Event C", 12));
        tree.add(makeEvent("Event D", 13));

        assertEquals(4, tree.height());
    }

}
