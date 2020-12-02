package graphlearn.bellmanford;

public class BellmanFordLearn<Weight extends Number & Comparable> {
    private long[] result;

    public boolean getShortestPaths(int n, Edge[] edges) {
        result = new long[n];
        for (int i = 1; i < n; i++) {
            result[i] = Integer.MAX_VALUE;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < edges.length; j++) {
                if (result[edges[j].end] > result[edges[j].star] + edges[j].weight) {
                    result[edges[j].end] = result[edges[j].star] + edges[j].weight;
                }
            }
        }

        boolean juedge = true;
        for (int i = 0; i < n; i++) {
            if (result[edges[i].end] > result[edges[i].star] + edges[i].weight) {
                juedge = false;
                break;
            }
        }
        return juedge;
    }

    class Edge {
        private int star;  // 边的起点
        private int end;   // 边的终点
        private int weight; // 边的权值

        Edge(int star, int end, int weight) {
            this.star = star;
            this.end = end;
            this.weight = weight;
        }
    }
}
