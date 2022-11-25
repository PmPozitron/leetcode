import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class TreeTraversalAndHeight {
    public static void main(String[] args) {
        expectedResultsForTraversalTasks().entrySet().stream()
                .forEach(item ->
                        System.out.printf("for %s expected result is %s and actual is %s\n",
                                item.getKey(), item.getValue(), calculateMaxTreeHeight(item.getKey())));
    }

    private static Map<TreeNode, List<Integer>> expectedResultsForTraversalTasks() {

        TreeNode one = new TreeNode(3, new TreeNode(1), new TreeNode(2));

        TreeNode two = new TreeNode(2);
        TreeNode three = new TreeNode(1, null, two);
        TreeNode four = new TreeNode(3, three, null);

        TreeNode five = new TreeNode(2);
        TreeNode six = new TreeNode(4, five, null);
        TreeNode seven = new TreeNode(3);
        TreeNode eight = new TreeNode(1, six, seven);

        TreeNode nine = new TreeNode(3, new TreeNode(1), new TreeNode(2));

        TreeNode ten = new TreeNode(7);
        TreeNode eleven = new TreeNode(5);
        TreeNode twelve = new TreeNode(4);
        TreeNode thirteen = new TreeNode(9);
        TreeNode fourteen = new TreeNode(1, ten, eleven);
        TreeNode fifteen = new TreeNode(2, twelve, thirteen);
        TreeNode sixteen = new TreeNode(3, fourteen, fifteen);

        return Map.of(
                sixteen, List.of(7, 1, 5, 3, 4, 2, 9),
                nine, List.of(1, 3, 2),
                one, List.of(1, 3, 2),
                three, List.of(1, 3, 2),
                four, List.of(1, 2, 3),
                eight, List.of(2, 4, 1, 3),
                new TreeNode(), Collections.emptyList(),
                new TreeNode(1), List.of(1)
        );
    }

    public static List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) return Collections.emptyList();

        Set<TreeNode> visited = new HashSet<>();
        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        List<Integer> result = new LinkedList<>();
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            if (visited.contains(node)) {
                result.add(node.val);
                continue;
            }
            if (node.left == null) {
                result.add(node.val);
                if (node.right != null)
                    queue.offerFirst(node.right);

            } else {
                if (node.right != null)
                    queue.offerFirst(node.right);

                queue.offerFirst(node);

                if (node.left != null)
                    queue.offerFirst(node.left);

                visited.add(node);
            }
        }
        return result;
    }

    public static List<Integer> inorderTraversalRecursive(TreeNode root) {
        if (root == null) return Collections.emptyList();
        List<Integer> result = new LinkedList<>();

        TreeNode current = root;
        result.addAll(inorderTraversalRecursive(current.left));
        result.add(current.val);
        result.addAll(inorderTraversalRecursive(current.right));

        return result;
    }

    public static int calculateMaxTreeHeight(TreeNode root) {
        if (root == null) return 0;

        int maxHeight = 1;
        ArrayDeque<Pair> queue = new ArrayDeque<>();
        Pair rootPair = new Pair(root, maxHeight);
        queue.offer(rootPair);

        while (!queue.isEmpty()) {
            Pair pair = queue.poll();
            if (pair.node.left != null) queue.offer(new Pair(pair.node.left, pair.height + 1));
            if (pair.node.right != null) queue.offer(new Pair(pair.node.right, pair.height + 1));

            if (maxHeight < pair.height) maxHeight = pair.height;
        }
        return maxHeight;
    }

    public static int calculateMinTreeHeight(TreeNode root) {
        if (root == null) return 0;

        int minHeight = Integer.MAX_VALUE;
        ArrayDeque<Pair> queue = new ArrayDeque<>();
        Pair rootPair = new Pair(root, 1);
        queue.offer(rootPair);

        while (!queue.isEmpty()) {
            Pair pair = queue.poll();
            if (pair.node.left != null) queue.offer(new Pair(pair.node.left, pair.height + 1));
            if (pair.node.right != null) queue.offer(new Pair(pair.node.right, pair.height + 1));

            if (pair.node.left == null && pair.node.right == null) {
                if (minHeight > pair.height) minHeight = pair.height;
            }
        }
        return minHeight;
    }

    public static int startMinDepthRecursive(TreeNode root) {
        if (root == null) return 0;

        return minDepthRecursive(root, 1, Integer.MAX_VALUE);
    }

    public static int minDepthRecursive(TreeNode current, int currentHeight, int currentMin) {
        if (current == null) return currentMin;

        if (current.left == null && current.right == null)
            if (currentMin > currentHeight)
                currentMin = (currentHeight);

        int left = minDepthRecursive(current.left, currentHeight + 1, currentMin);
        int right = minDepthRecursive(current.right, currentHeight + 1, currentMin);

        if (left <= right) return left;
        else return right;
    }

    public static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;

        return hasPathSum(root, targetSum, root.val);
    }

    public static boolean hasPathSum(TreeNode current, int targetSum, int currentSum) {
        if (current == null) return false;

        if (current.left == null && current.right == null)
            return targetSum == currentSum;

        else if (current.left == null)
            return hasPathSum(current.right, targetSum, currentSum + current.right.val);

        else if (current.right == null)
            return hasPathSum(current.left, targetSum, currentSum + current.left.val);

        else return hasPathSum(current.left, targetSum, currentSum + current.left.val) ||
                    hasPathSum(current.right, targetSum, currentSum + current.right.val);

    }

    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q != null) return false;
        if (p != null && q == null) return false;
        if (p == null && q == null) return true;

        Queue<TreeNode> pQ = new ArrayDeque<>();
        Queue<TreeNode> qQ = new ArrayDeque<>();
        pQ.offer(p);
        qQ.offer(q);

        while (!pQ.isEmpty()) {
            if (qQ.isEmpty()) return false;

            TreeNode pNode = pQ.poll();
            TreeNode qNode = qQ.poll();

            if (!isSameNode(pNode, qNode)) return false;

            if (pNode.left != null) pQ.offer(pNode.left);
            if (pNode.right != null) pQ.offer(pNode.right);
            if (qNode.left != null) qQ.offer(qNode.left);
            if (qNode.right != null) qQ.offer(qNode.right);
        }

        if (!qQ.isEmpty()) return false;

        return true;
    }

    private static boolean isSameNode(TreeNode p, TreeNode q) {
        boolean pLeftPresent = false;
        boolean pRightPresent = false;
        boolean qLeftPresent = false;
        boolean qRightPresent = false;

        if (p.left != null) {
            pLeftPresent = true;
        }
        if (p.right != null) {
            pRightPresent = true;
        }

        if (q.left != null) {
            qLeftPresent = true;
        }
        if (q.right != null) {
            qRightPresent = true;
        }

        return (pLeftPresent == qLeftPresent) &&
                (pRightPresent == qRightPresent) &&
                (p.val == q.val);
    }

    public static boolean isSymmetricRecursive(TreeNode root) {
        if (root == null) return false;

        if (root.left == null && root.right != null) return false;
        if (root.left != null && root.right == null) return false;
        if (root.left == null && root.right == null) return true;

        return isSymmetricRecursive(root.left, root.right);
    }

    private static boolean isSymmetricRecursive(TreeNode left, TreeNode right) {

        if (left == null && right != null) return false;
        if (left != null && right == null) return false;
        if (left == null && right == null) return true;

        if ((left.left == null) && (right.right != null)) return false;
        if ((left.left != null) && (right.right == null)) return false;
        if ((left.right == null) && (right.left != null)) return false;
        if ((left.right != null) && (right.left == null)) return false;

        if (!isSymmetricNode(left, right)) return false;

        return isSymmetricRecursive(left.left, right.right) &&
                isSymmetricRecursive(left.right, right.left);
    }

    public static boolean isSymmetric(TreeNode root) {
        if (root == null) return false;

        if (root.left == null && root.right != null) return false;
        if (root.left != null && root.right == null) return false;
        if (root.left == null && root.right == null) return true;

        Queue<TreeNode> leftQueue = new ArrayDeque<>();
        Queue<TreeNode> rightQueue = new ArrayDeque<>();

        leftQueue.offer(root.left);
        rightQueue.offer(root.right);

        while (!leftQueue.isEmpty()) {
            if (rightQueue.isEmpty()) return false;

            TreeNode left = leftQueue.poll();
            TreeNode right = rightQueue.poll();

            if (!isSymmetricNode(left, right)) return false;

            if (left.left != null) leftQueue.offer(left.left);
            if (left.right != null) leftQueue.offer(left.right);

            if (right.right != null) rightQueue.offer(right.right);
            if (right.left != null) rightQueue.offer(right.left);
        }

        if (!rightQueue.isEmpty()) return false;

        return true;
    }

    private static boolean isSymmetricNode(TreeNode p, TreeNode q) {
        boolean pLeftPresent = false;
        boolean pRightPresent = false;
        boolean qLeftPresent = false;
        boolean qRightPresent = false;

        if (p.left != null) {
            pLeftPresent = true;
        }
        if (p.right != null) {
            pRightPresent = true;
        }

        if (q.left != null) {
            qLeftPresent = true;
        }
        if (q.right != null) {
            qRightPresent = true;
        }

        return (pLeftPresent == qRightPresent) &&
                (pRightPresent == qLeftPresent) &&
                (p.val == q.val);
    }

}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Pair {
    TreeNode node;
    Integer height;

    public Pair(TreeNode node, Integer height) {
        this.node = node;
        this.height = height;
    }
}
