import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class GraphTasks {

    public static void main(String[] args) throws IOException {
//        System.out.println(validPath(3, new int[][]{{0,1},{1,2},{2,0}}, 0, 2));
//        System.out.println(validPath(1, new int[][]{}, 0, 0));
//        System.out.println(validPathViaIterativeDfs(10, new int[][]{{0, 7}, {0, 8}, {6, 1}, {2, 0}, {0, 4}, {5, 8}, {4, 7}, {1, 3}, {3, 5}, {6, 5}}, 7, 5));
//        System.out.println(validPathViaAdjacencyMatrix(2000, readHugeValidPathTaskFromFile(), 473, 901));

//        int[][] input = {{1,1,0},{1,1,0},{0,0,1}};
//        int[][] input = {{1,0,0},{0,1,0},{0,0,1}};
        int[][] input = {{1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, {0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0}, {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}};
        System.out.println(findCircleNum(input));

//        for (int i = 0; i < 1000; i++) {
//            long start = System.nanoTime();
//            findCircleNum(input);
//            System.out.println(System.nanoTime()-start);
//        }
    }

    /*
    https://leetcode.com/problems/number-of-provinces/description/
    https://leetcode.com/problems/number-of-provinces/discussion/comments/1723380
    Input: isConnected = [[1,1,0],[1,1,0],[0,0,1]]
    Output: 2
        0   1   2
        -   -   -
    0|  1   1   0
    1|  1   1   0
    2|  0   0   1
     */
    public static int findCircleNum(int[][] isConnected) {
        fillAdjacencyMatrix(isConnected);
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        HashSet<Integer> visited = new HashSet<>();

        for (int i = 0; i < isConnected.length; i++) {
            List<Integer> reachableVertices = graph.get(i);
            if (reachableVertices == null) {
                reachableVertices = new ArrayList<>(isConnected.length);
                graph.put(i, reachableVertices);
            }

            for (int j = 0; j < isConnected.length; j++) {
                if (isConnected[i][j] == 0) continue;

                reachableVertices.add(j);
            }
        }

        int result = 0;
        for (Map.Entry<Integer, List<Integer>> entry : graph.entrySet()) {
            if (visited.add(entry.getKey())) result++;
            for (Integer reachableVertex: entry.getValue()) {
                visited.add(reachableVertex);
            }

        }
        return result;
    }

    /*
    https://leetcode.com/problems/find-if-path-exists-in-graph/
    Input: n = 3, edges = [[0,1],[1,2],[2,0]], source = 0, destination = 2
    Output: true
            0   1   2
            _   _   _
        0|  0   1   0
        1|  0   0   1
        2|  1   0   0
    */
    /*

            0   1   2   3   4   5   6   7   8   9
            -   -   -   -   -   -   -   -   -   -
        0|  0   0   0   0   1   0   0   1   1   0
        1|  0   0   0   1   0   0   0   0   0   0
        2|  1   0   0   0   0   0   0   0   0   0
        3|  0   0   0   0   0   1   0   0   0   0
        4|  0   0   0   0   0   0   0   1   0   0
        5|  0   0   0   0   0   0   0   0   1   0
        6|  0   1   0   0   0   1   0   0   0   0
        7|  0   0   0   0   0   0   0   0   0   0
        8|  0   0   0   0   0   0   0   0   0   0
        9|  0   0   0   0   0   0   0   0   0   0
     */

    public static boolean validPathViaIterativeDfs(int n, int[][] edges, int source, int destination) {
        HashMap<Integer, List<Integer>> graph = new HashMap<>();

        for (int i = 0; i < edges.length; i++) {
            graph.merge(edges[i][0], new ArrayList<>(List.of(edges[i][1])), (anOld, aNew) -> {
                anOld.add(aNew.get(0));
                return anOld;
            });
            graph.merge(edges[i][1], new ArrayList<>(List.of(edges[i][0])), (anOld, aNew) -> {
                anOld.add(aNew.get(0));
                return anOld;
            });
        }

        ArrayDeque<Integer> queue = new ArrayDeque<>();
        HashSet<Integer> visited = new HashSet<>();
        queue.push(source);

        while (!queue.isEmpty()) {
            if (queue.peek() == destination) return true;

            Integer vertex = queue.pop();
            graph.get(vertex).stream()
                    .filter(visited::add)
                    .forEach(queue::push);
        }
        return false;
    }

    // this approach (Warshall's algorithm from Lafore's book on algorithms in java) fails given huge input with "time exceeded"
    // fuck...
    // update: it has not much to do with this task, I mean Warshal's algo definitely can solve such a task, but it does more than just that
    // one should use Warshal's algo for prepopulating adjacency matrix
    // for later using that matrix for solving such tasks (is there a path ?) in constant time instead of linear, that plain BFS/DFS provides
    public static boolean validPathViaAdjacencyMatrix(int n, int[][] edges, int source, int destination) {

        int[][] adjacencyMatrix = new int[n][n];

        for (int i = 0; i < edges.length; i++) {
            adjacencyMatrix[edges[i][0]][edges[i][1]] = 1;
        }

        fillAdjacencyMatrix(adjacencyMatrix);
        return adjacencyMatrix[source][destination] == 1;
    }

    private static void fillAdjacencyMatrix(int[][] adjacencyMatrix) {
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    for (int k = 0; k < adjacencyMatrix[i].length; k++) {
                        if (adjacencyMatrix[k][i] == 1) {
                            adjacencyMatrix[k][j] = 1;
                            adjacencyMatrix[j][k] = 1;
                        }
                    }
                }
            }
        }
    }

    private static int[][] readHugeValidPathTaskFromFile() throws IOException {
        List<String> list = Files.readAllLines(Paths.get("src/main/resources/validPathHugeTask.txt"));

        String[] array = list.get(0).split("\\},\\{");
        array[0] = array[0].replace("{", "");
        array[array.length - 1] = array[array.length - 1].replace("}", "");
        array[array.length - 1] = array[array.length - 1].replace(";", "");
        int[][] edges = new int[5000][2];
        for (int i = 0; i < edges.length; i++) {
            String pair = array[i];
            edges[i][0] = Integer.valueOf(pair.split(",")[0]);
            edges[i][0] = Integer.valueOf(pair.split(",")[1]);
        }

        return edges;
    }
}