//imports all requiered utilities
import java.util.List;

public record SearchResult(List<String> route, List<String> visitedOrder, int maxFrontier) {
    public boolean found() {
        return !route.isEmpty();
    }

    public int routeLengthInEdges() {
        return route.isEmpty() ? -1 : route.size() - 1;
    }

    public int verticesVisited() {
        return visitedOrder.size();
    }
}
