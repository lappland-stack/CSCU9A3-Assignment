public class GraphExperiment {
    public static void main(String[] args) {
        investigate("Graph A", GraphData.graphA(), "Entrance", "Acoustic Stage", true);
        investigate("Graph B", GraphData.graphB(), "Entrance", "Acoustic Stage", true);
        investigate("Graph B", GraphData.graphB(), "Entrance", "Workshop", true);

        // Replace this with your student ID number specified in the assessment instructions.
        //shit i guess i will
        long seed = 3546619L; //i aint taking no L tho
        SiteGraph generated = GraphData.generatedGraph(seed);
        investigate("Generated graph (seed " + seed + ")", generated, "L0", "L17", true);
    }

    private static void investigate(String name, SiteGraph graph,
                                    String start, String goal, boolean visualise) {
        SearchResult bfs = graph.breadthFirstSearch(start, goal);
        SearchResult dfs = graph.depthFirstSearch(start, goal);
        String label = name + ": " + start + " -> " + goal;

        System.out.printf("%n%s%n", label);
        print("BFS", bfs);
        print("DFS", dfs);

        if (visualise) {
            GraphVisualiser.show(graph, bfs, label + " - BFS");
            GraphVisualiser.show(graph, dfs, label + " - DFS");
        }

        // TODO Task 4b: record the maximum frontier size for each search
    }

    private static void print(String algorithm, SearchResult result) {
        System.out.printf("%-4s route=%s, edges=%d, vertices visited=%d%n",
                algorithm, result.route(), result.routeLengthInEdges(), result.verticesVisited(), result.maxFrontier());
    }
}
