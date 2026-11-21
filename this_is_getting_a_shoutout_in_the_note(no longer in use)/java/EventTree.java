/*
there deffo has to be a way to simplify all the imports, like import only once in a method and then call that
method to import into every other file
first we import the local data and time functionality
then we import the array functionality and then the list functionality

now im thinking i could just say all utility classes at the top and just not repeat myself each time
 */
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Binary search tree ordered by event start time, then title. */
//explained it for me?
    //seems like it, guess i wont comment all the rest and ill quickfire the rest of the files
public class EventTree {
    private static class Node {
        FestivalEvent event;
        Node left;
        Node right;

        Node(FestivalEvent event) {
            this.event = event;
        }
    }

    public record SearchResult(FestivalEvent event, int nodesVisited) { }

    private Node root;
    private int size;

    public int size() {
        return size;
    }

    public void add(FestivalEvent event) {
        if (event == null) throw new IllegalArgumentException("event cannot be null");
        root = add(root, event);
    }

    private Node add(Node node, FestivalEvent event) {
        if (node == null) {
            size++;
            return new Node(event);
        }
        int cmp = compare(event, node.event);
        if (cmp < 0) node.left = add(node.left, event);
        else if (cmp > 0) node.right = add(node.right, event);
        else node.event = event;
        return node;
    }

    public boolean remove(FestivalEvent event) {
        int before = size;
        root = remove(root, event);
        return size < before;
    }

    private Node remove(Node node, FestivalEvent event) {
        if (node == null) return null;
        int cmp = compare(event, node.event);
        if (cmp < 0) node.left = remove(node.left, event);
        else if (cmp > 0) node.right = remove(node.right, event);
        else {
            size--;
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node successor = smallest(node.right);
            node.event = successor.event;
            node.right = removeWithoutCounting(node.right, successor.event);
        }
        return node;
    }

    private Node removeWithoutCounting(Node node, FestivalEvent event) {
        if (node == null) return null;
        int cmp = compare(event, node.event);
        if (cmp < 0) node.left = removeWithoutCounting(node.left, event);
        else if (cmp > 0) node.right = removeWithoutCounting(node.right, event);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node successor = smallest(node.right);
            node.event = successor.event;
            node.right = removeWithoutCounting(node.right, successor.event);
        }
        return node;
    }

    private Node smallest(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    /**
     * Searches using the same ordering as insertion.
     * Returns the matching event (or null if there is none) together with the number of
     * tree nodes examined during the search.
     *
     * Task 2c: nodesVisited is currently always reported as 0. Amend this method so that it
     * reports the number of nodes examined, without changing which event it returns.
     */
    public SearchResult find(LocalDateTime startTime, String title) {
        Node current = root;
        while (current != null) {
            int cmp = compare(startTime, title, current.event);
            if (cmp == 0) return new SearchResult(current.event, 0);
            current = cmp < 0 ? current.left : current.right;
        }
        return new SearchResult(null, 0);
    }

    /**
     * Task 2a: implement this recursively.
     * This starter project defines an empty tree as height 0 and a one-node tree as height 1.
     */
    public int height() {
        // TODO Task 2a
        return 0;
    }

    // You will probably want a private recursive helper for height().

    public String sideways() {
        StringBuilder out = new StringBuilder();
        sideways(root, 0, out);
        return out.toString();
    }

    private void sideways(Node node, int depth, StringBuilder out) {
        if (node == null) return;
        sideways(node.right, depth + 1, out);
        out.append("    ".repeat(depth))
           .append(node.event.getStartTime().toLocalTime())
           .append(" ")
           .append(node.event.getTitle())
           .append(System.lineSeparator());
        sideways(node.left, depth + 1, out);
    }

    private int compare(FestivalEvent a, FestivalEvent b) {
        return compare(a.getStartTime(), a.getTitle(), b);
    }

    private int compare(LocalDateTime start, String title, FestivalEvent b) {
        int c = start.compareTo(b.getStartTime());
        if (c != 0) return c;
        return title.compareToIgnoreCase(b.getTitle());
    }
}
