import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkPermission;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphTasks {

    public static void main(String[] args) throws IOException {
//        System.out.println(validPath(3, new int[][]{{0,1},{1,2},{2,0}}, 0, 2));
//        System.out.println(validPath(1, new int[][]{}, 0, 0));
//        System.out.println(validPathViaIterativeDfs(10, new int[][]{{0, 7}, {0, 8}, {6, 1}, {2, 0}, {0, 4}, {5, 8}, {4, 7}, {1, 3}, {3, 5}, {6, 5}}, 7, 5));
//        System.out.println(validPathViaAdjacencyMatrix(2000, readHugeValidPathTaskFromFile(), 473, 901));

//        int[][] input = {{1,1,0},{1,1,0},{0,0,1}};
//        int[][] input = {{1,0,0},{0,1,0},{0,0,1}};
//        int[][] input = {{1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, {0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0}, {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}};
//        System.out.println(findCircleNum(input));
//        int[][] input = {{0,7},{0,8},{6,1},{2,0},{0,4},{5,8},{4,7},{1,3},{3,5},{6,5}};
//        int[][] input = {{141, 237}, {197, 291}, {47, 228}, {114, 332}, {457, 199}, {432, 160}, {123, 1}, {483, 307}, {374, 63}, {345, 54}, {470, 204}, {402, 487}, {296, 269}, {96, 95}, {315, 462}, {445, 201}, {146, 417}, {398, 379}, {407, 225}, {340, 66}, {137, 381}, {244, 386}, {90, 267}, {234, 366}, {335, 177}, {347, 394}, {172, 38}, {208, 251}, {133, 310}, {325, 343}, {77, 82}, {354, 372}, {438, 494}, {327, 91}, {97, 139}, {124, 283}, {363, 421}, {451, 483}, {69, 456}, {421, 486}, {365, 249}, {75, 469}, {460, 11}, {488, 48}, {287, 245}, {158, 210}, {333, 89}, {136, 399}, {443, 482}, {31, 423}, {174, 278}, {269, 182}, {161, 206}, {427, 359}, {251, 93}, {369, 327}, {428, 110}, {166, 280}, {342, 189}, {168, 440}, {128, 326}, {5, 318}, {276, 118}, {134, 345}, {380, 124}, {58, 146}, {147, 263}, {425, 254}, {499, 183}, {416, 52}, {473, 15}, {113, 262}, {142, 180}, {121, 357}, {222, 147}, {16, 459}, {339, 97}, {13, 46}, {35, 75}, {173, 354}, {467, 287}, {50, 19}, {272, 356}, {219, 178}, {111, 51}, {84, 136}, {80, 29}, {332, 32}, {343, 0}, {481, 385}, {414, 451}, {281, 368}, {98, 240}, {204, 192}, {43, 169}, {323, 27}, {378, 64}, {254, 320}, {106, 474}, {126, 20}, {322, 202}, {329, 434}, {479, 80}, {489, 308}, {17, 171}, {198, 498}, {379, 335}, {192, 31}, {3, 138}, {150, 92}, {482, 220}, {440, 315}, {336, 150}, {153, 460}, {214, 68}, {387, 3}, {455, 218}, {217, 40}, {401, 349}, {313, 12}, {284, 250}, {405, 312}, {348, 300}, {105, 226}, {228, 246}, {278, 450}, {399, 109}, {25, 94}, {246, 467}, {183, 193}, {406, 398}, {152, 403}, {250, 83}, {259, 369}, {350, 409}, {249, 412}, {238, 281}, {2, 211}, {22, 130}, {338, 476}, {18, 209}, {433, 151}, {478, 285}, {485, 33}, {237, 380}, {476, 373}, {331, 121}, {11, 14}, {29, 123}, {453, 247}, {376, 292}, {159, 370}, {293, 47}, {178, 351}, {91, 221}, {206, 328}, {165, 55}, {264, 90}, {55, 133}, {9, 429}, {351, 205}, {417, 361}, {493, 427}, {212, 148}, {256, 491}, {419, 200}, {247, 44}, {8, 471}, {32, 73}, {122, 492}, {82, 337}, {4, 355}, {248, 388}, {303, 129}, {60, 108}, {320, 104}, {138, 35}, {203, 188}, {444, 437}, {475, 391}, {341, 410}, {368, 376}, {300, 248}, {255, 342}, {73, 36}, {230, 98}, {127, 145}, {314, 477}, {486, 215}, {193, 23}, {459, 22}, {283, 470}, {164, 257}, {151, 382}, {290, 96}, {311, 336}, {359, 105}, {227, 371}, {92, 195}, {439, 364}, {149, 140}, {324, 301}, {72, 117}, {288, 431}, {226, 131}, {38, 464}, {305, 495}, {185, 126}, {253, 481}, {349, 309}, {282, 461}, {461, 216}, {118, 158}, {155, 102}, {148, 16}, {268, 127}, {492, 400}, {375, 273}, {115, 9}, {167, 244}, {224, 329}, {306, 457}, {411, 392}, {286, 120}, {474, 135}, {89, 141}, {39, 30}, {187, 25}, {210, 2}, {64, 5}, {87, 76}, {188, 170}, {494, 275}, {324, 317}, {196, 452}, {181, 231}, {353, 418}, {295, 478}, {355, 435}, {279, 352}, {261, 271}, {104, 6}, {330, 447}, {103, 88}, {144, 305}, {36, 413}, {395, 333}, {120, 485}, {334, 8}, {182, 341}, {140, 53}, {364, 294}, {304, 7}, {465, 405}, {61, 496}, {309, 77}, {79, 484}, {163, 270}, {310, 448}, {211, 242}, {265, 497}, {242, 113}, {88, 259}, {415, 234}, {81, 295}, {236, 322}, {23, 241}, {145, 17}, {298, 395}, {436, 284}, {413, 186}, {74, 303}, {108, 34}, {68, 396}, {180, 274}, {129, 272}, {184, 258}, {207, 252}, {383, 69}, {213, 416}, {462, 402}, {201, 74}, {301, 203}, {209, 321}, {396, 235}, {277, 56}, {143, 377}, {49, 428}, {56, 103}, {371, 197}, {157, 299}, {0, 367}, {430, 415}, {34, 288}, {51, 208}, {139, 24}, {24, 101}, {497, 152}, {125, 28}, {93, 490}, {266, 207}, {390, 179}, {437, 222}, {169, 276}, {346, 119}, {156, 159}, {446, 455}, {337, 149}, {458, 239}, {231, 425}, {241, 164}, {76, 217}, {135, 125}, {362, 153}, {429, 389}, {271, 194}, {273, 282}, {223, 390}, {53, 286}, {367, 353}, {487, 442}, {403, 223}, {232, 253}, {326, 72}, {194, 106}, {186, 397}, {131, 190}, {102, 181}, {62, 449}, {67, 70}, {26, 466}, {388, 173}, {116, 260}, {216, 340}, {442, 298}, {245, 302}, {243, 142}, {382, 384}, {40, 489}, {42, 62}, {205, 43}, {189, 420}, {484, 404}, {160, 407}, {65, 424}, {431, 26}, {14, 441}, {130, 350}, {463, 84}, {270, 347}, {386, 156}, {190, 57}, {308, 419}, {263, 436}, {410, 61}, {41, 499}, {215, 378}, {57, 348}, {78, 261}, {27, 363}, {52, 444}, {360, 438}, {384, 297}, {258, 430}, {312, 375}, {46, 346}, {448, 224}, {199, 362}, {394, 122}, {220, 184}, {422, 18}, {468, 393}, {28, 4}, {302, 99}, {15, 167}, {202, 37}, {117, 67}, {162, 360}, {389, 243}, {119, 331}, {179, 458}, {408, 265}, {426, 175}, {262, 59}, {267, 154}, {100, 401}, {225, 408}, {318, 472}, {30, 488}, {294, 81}, {356, 65}, {1, 387}, {171, 465}, {385, 13}, {191, 330}, {477, 256}, {99, 219}, {132, 338}, {412, 107}, {289, 304}, {435, 236}, {449, 374}, {94, 313}, {59, 168}, {109, 493}, {490, 446}, {372, 233}, {456, 264}, {10, 214}, {95, 157}, {409, 45}, {358, 314}, {469, 454}, {66, 422}, {83, 42}, {464, 86}, {86, 163}, {441, 100}, {239, 41}, {480, 10}, {297, 358}, {33, 143}, {328, 21}, {280, 293}, {154, 137}, {472, 58}, {496, 112}, {54, 344}, {110, 227}, {235, 50}, {392, 339}, {175, 296}, {361, 365}, {45, 116}, {12, 255}, {101, 289}, {352, 433}, {397, 166}, {357, 71}, {424, 111}, {85, 432}, {498, 185}, {195, 196}, {6, 161}, {292, 453}, {285, 198}, {19, 268}, {454, 162}, {491, 290}, {63, 79}, {48, 279}, {404, 128}, {274, 39}, {321, 463}, {218, 191}, {420, 473}, {370, 238}, {176, 323}, {319, 426}, {112, 134}, {434, 85}, {233, 445}, {44, 49}, {200, 480}, {466, 383}, {257, 311}, {229, 439}, {107, 78}, {393, 319}, {471, 213}, {366, 232}, {373, 144}, {447, 115}, {307, 155}, {70, 325}, {240, 468}, {21, 230}, {316, 174}, {37, 132}, {299, 212}, {275, 443}, {452, 406}, {423, 411}, {377, 334}, {221, 176}, {418, 266}, {400, 306}, {391, 60}, {450, 87}, {495, 172}, {20, 479}, {344, 165}, {381, 277}, {177, 229}, {7, 316}, {170, 187}, {291, 475}, {260, 414}, {252, 114}};

//        for (int i = 0; i < 1000; i++) {
//            long start = System.nanoTime();
//            validPathViaAdjacencyMatrix(500, input, 324, 317);
//            System.out.println(System.nanoTime() - start);
//        }
//        System.out.println("============");
//        for (int i = 0; i < 1000; i++) {
//            long start = System.nanoTime();
//            validPathViaAdjacencySet(500, input, 324, 317);
//            System.out.println(System.nanoTime() - start);
//        }
//        System.out.println(validPathViaAdjacencyMatrix(10, input, 7, 5));
//        System.out.println(validPathViaAdjacencySet(500, input, 324, 317));

//        for (int i = 0; i < 1000; i++) {
//            long start = System.nanoTime();
//            findCircleNum(input);
//            System.out.println(System.nanoTime()-start);
//        }

        System.out.println(numIslands(new char[][]{{'1','0','0','1','0','1','0','0','0','0','1','0','0','0','1','0','1','0','1','1'},{'1','0','1','0','0','0','0','0','0','1','0','0','0','1','0','1','0','0','0','0'}}));
    }

    /*
    https://leetcode.com/problems/number-of-islands/
    Input: grid = [
            0   1   2   3   4
            -   -   -   -   -
      0|  ["1","1","0","0","0"],
      1|  ["1","1","0","0","0"],
      2|  ["0","0","1","0","0"],
      3|  ["0","0","0","1","1"]
    ]
        Output: 3
     */
    public static int numIslands(char[][] grid) {
        if (grid.length == 0) return 0;

        HashMap<Integer, Set<Integer>> land = new LinkedHashMap<>();
        HashMap<Integer, Set<Integer>> water = new LinkedHashMap<>();

        int counter = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '0') water.put(counter++, new HashSet<>());
                else land.put(counter++, new HashSet<>());
            }
        }

        if (land.size() == 0) return 0;

        int result = 0;
        HashSet<Integer> visited = new LinkedHashSet<>();
        ArrayDeque<Integer> stack = new ArrayDeque<>();

        for (Integer root : land.keySet()) {
            if (visited.contains(root)) continue;

            stack.push(root);

            while (! stack.isEmpty()) {
                Integer adjacent = stack.pop();
                visited.add(adjacent);
                if (adjacent > 0 && adjacent % grid[0].length != 0) {
                    if (land.containsKey(adjacent-1) && ! visited.contains(adjacent-1)) {
                        land.get(root).add(adjacent-1);
                        stack.push(adjacent-1);
                    }
                }
                if (adjacent > grid[0].length) {
                    if (land.containsKey(adjacent-grid[0].length) && ! visited.contains(adjacent-grid[0].length)) {
                        land.get(root).add(adjacent-grid[0].length);
                        stack.push(adjacent-grid[0].length);
                    }
                }
                if (adjacent + 1 < counter && adjacent % grid[0].length < grid[0].length - 1) {
                    if (land.containsKey(adjacent+1) && ! visited.contains(adjacent+1)) {
                        land.get(root).add(adjacent+1);
                        stack.push(adjacent+1);
                    }
                }
                if (adjacent + grid[0].length < counter) {
                    if (land.containsKey(adjacent+grid[0].length)  && ! visited.contains(adjacent+grid[0].length)) {
                        land.get(root).add(adjacent+grid[0].length);
                        stack.push(adjacent+grid[0].length);
                    }
                }
            }
            result++;
        }
        return result;
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
            for (Integer reachableVertex : entry.getValue()) {
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

    /*
    maybe I did something wrong but this solution is significantly (a 100-250 times) slower, than adjacencyMatrix approach.
    it was not accepted on leetcode therefore
     */
    public static boolean validPathViaAdjacencySet(int n, int[][] edges, int source, int destination) {
        Map<Integer, Set<Integer>> graph = fillAdjacencySet(n, edges);

        return graph.get(source).contains(destination);
    }

    private static Map<Integer, Set<Integer>> fillAdjacencySet(int numberOfVertices, int[][] edges) {
        HashMap<Integer, Set<Integer>> result = new LinkedHashMap<>();
        for (int i = 0; i < numberOfVertices; i++) {
            result.put(i, new HashSet<>());
        }

        for (int i = 0; i < edges.length; i++) {
            result.get(edges[i][0]).add(edges[i][1]);
            result.get(edges[i][1]).add(edges[i][0]);
        }

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        for (Map.Entry<Integer, Set<Integer>> entry : result.entrySet()) {
            for (Integer vertex : entry.getValue()) {
                stack.push(vertex);
            }
            while (!stack.isEmpty()) {
                Integer adjacent = stack.pop();
                for (Integer vertex : result.get(adjacent)) {
                    if (vertex != entry.getKey())
                        if (entry.getValue().add(vertex))
                            stack.push(vertex);
                }
            }
        }

        return result;
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