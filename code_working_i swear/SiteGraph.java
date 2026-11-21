import java.util.*;

/** Undirected graph representing walkable paths between festival locations. */
public class SiteGraph {
    private final Map<String, LinkedHashSet<String>> adjacency = new LinkedHashMap<>();

    public void addLocation(String name) {
        adjacency.computeIfAbsent(name, k -> new LinkedHashSet<>());
    }

    public void addPath(String a, String b) {
        addLocation(a);
        addLocation(b);
        adjacency.get(a).add(b);
        adjacency.get(b).add(a);
    }

    public Set<String> getLocations() {
        return Collections.unmodifiableSet(adjacency.keySet());
    }

    public Set<String> getNeighbours(String location) {
        LinkedHashSet<String> neighbours = adjacency.get(location);
        if (neighbours == null) return Set.of();
        return Collections.unmodifiableSet(neighbours);
    }

    /** Working BFS supplied for Task 4. */
    public SearchResult breadthFirstSearch(String start, String goal) {
        requireLocations(start, goal);
        Queue<String> frontier = new ArrayDeque<>();
        Map<String, String> parent = new HashMap<>();
        List<String> visitedOrder = new ArrayList<>();
        Set<String> discovered = new HashSet<>();

        frontier.add(start);
        discovered.add(start);

        while (!frontier.isEmpty()) {
            String current = frontier.remove();
            visitedOrder.add(current);
            if (current.equals(goal)) return result(start, goal, parent, visitedOrder);

            for (String neighbour : adjacency.get(current)) {
                if (discovered.add(neighbour)) {
                    parent.put(neighbour, current);
                    frontier.add(neighbour);
                }
            }
        }
        return new SearchResult(List.of(), List.copyOf(visitedOrder));
    }

    /** Working iterative DFS supplied for Task 4. */
    public SearchResult depthFirstSearch(String start, String goal) {
        requireLocations(start, goal);
        Deque<String> frontier = new ArrayDeque<>();
        Map<String, String> parent = new HashMap<>();
        List<String> visitedOrder = new ArrayList<>();
        Set<String> discovered = new HashSet<>();

        frontier.push(start);
        discovered.add(start);

        while (!frontier.isEmpty()) {
            String current = frontier.pop();
            visitedOrder.add(current);
            if (current.equals(goal)) return result(start, goal, parent, visitedOrder);

            // Reverse so the first-added neighbour is explored first in this stack implementation.
            List<String> neighbours = new ArrayList<>(adjacency.get(current));
            Collections.reverse(neighbours);
            for (String neighbour : neighbours) {
                if (discovered.add(neighbour)) {
                    parent.put(neighbour, current);
                    frontier.push(neighbour);
                }
            }
        }
        return new SearchResult(List.of(), List.copyOf(visitedOrder));
    }

    private SearchResult result(String start, String goal, Map<String, String> parent,
                                List<String> visitedOrder) {
        LinkedList<String> route = new LinkedList<>();
        String current = goal;
        route.addFirst(current);
        while (!current.equals(start)) {
            current = parent.get(current);
            if (current == null) return new SearchResult(List.of(), List.copyOf(visitedOrder));
            route.addFirst(current);
        }
        return new SearchResult(List.copyOf(route), List.copyOf(visitedOrder));
    }

    private void requireLocations(String start, String goal) {
        if (!adjacency.containsKey(start) || !adjacency.containsKey(goal))
            throw new IllegalArgumentException("Unknown start or goal location");
    }
}
