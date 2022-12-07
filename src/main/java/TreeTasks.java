import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class TreeTasks {
    public static void main(String[] args) {
//        expectedResultsForTraversalTasks().entrySet().stream()
//                .forEach(item ->
//                        System.out.printf("for %s expected result is %s and actual is %s\n",
//                                item.getKey(), item.getValue(), calculateMaxTreeHeight(item.getKey())));

//        expectedResultsForArrayToBst().entrySet().stream()
//                .forEach((item -> System.out.printf("for %s expected result is %s and actual is %s\n",
//                        item.getKey(), item.getValue(), sortedArrayToBST(new int[]{-10,-3,0,5,9}))));
//                        item.getKey(), item.getValue(), sortedArrayToBST(new int[]{0,1,2,3,4,5,6,7,8}))));

        expectedResultsForIsBalanced().entrySet().stream()
                .forEach(entry -> System.out.printf("for %s expected is %b and actual is %b\n", entry.getKey(), entry.getValue(), isBalanced(entry.getKey())));
    }

    private static Map<TreeNode, List<Integer>> expectedResultsForArrayToBst() {
        TreeNode negativeThree = new TreeNode(-3, new TreeNode(-10), null);
        TreeNode positiveFive = new TreeNode(5, null, new TreeNode(9));
        return Map.of(new TreeNode(0, negativeThree, positiveFive), List.of(-10,-3,0,5,9));

//        TreeNode one = new TreeNode(1, new TreeNode(0), new TreeNode(2));
//        TreeNode five = new TreeNode(5, new TreeNode(4), null);
//        return Map.of(new TreeNode(3, one, five), List.of(3,1,5,0,2,4));
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

    public static TreeNode sortedArrayToBST(int[]nums) {
        if (nums.length == 0)
            return null;
        if (nums.length == 1)
            return new TreeNode(nums[0]);
        if (nums.length == 2)
            return new TreeNode(nums[0], null, new TreeNode(nums[1]));
        if (nums.length == 3)
            return new TreeNode(nums[1], new TreeNode(nums[0]), new TreeNode(nums[2]));

        int median = nums.length / 2;
        TreeNode first = sortedArrayToBST(nums, 0, median-1);
        TreeNode second = sortedArrayToBST(nums, median+1, nums.length-1);

        TreeNode result = new TreeNode(nums[median], first, second);

        return result;
    }

    private static TreeNode sortedArrayToBST(int[]nums, int leftEdge, int rightEdge) {
        if (rightEdge-leftEdge == 0)
            return new TreeNode(nums[rightEdge]);
        if (rightEdge-leftEdge == 1)
            return new TreeNode(nums[leftEdge], null, new TreeNode(nums[rightEdge]));
        if (rightEdge-leftEdge == 2)
            return new TreeNode(nums[leftEdge+1], new TreeNode(nums[leftEdge]), new TreeNode(nums[rightEdge]));

        int median = leftEdge + (rightEdge-leftEdge)/2;
        TreeNode first = sortedArrayToBST(nums, leftEdge, median-1);
        TreeNode second = sortedArrayToBST(nums, median+1, rightEdge);

        TreeNode result = new TreeNode(nums[median], first, second);

        return result;
    }

    // not quite correct due to a bit surprising 'height-balanced' definition on leetcode
    // only recursive approach (was borrowed from discussion) was accepted
    // https://leetcode.com/problems/balanced-binary-tree/discussion/comments/1673794
    public static boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        Queue<Pair> queue = new ArrayDeque<>();
        int minHeight = Integer.MAX_VALUE;
        int maxHeight = 0;

        queue.offer(new Pair(root, 1));
        while (!queue.isEmpty()) {
            Pair current = queue.poll();
            if ((current.node.left == null && current.node.right != null) || (current.node.left !=null && current.node.right == null)) return false;

            if (current.node.left == null && current.node.right == null) {
                if (current.height > maxHeight) maxHeight = current.height;
                if (current.height < minHeight) minHeight = current.height;
            }


            if (current.node.left != null) queue.offer(new Pair(current.node.left, current.height + 1));
            if (current.node.right != null) queue.offer(new Pair(current.node.right, current.height + 1));
        }

        return maxHeight - minHeight <= 1;
    }

    // slight modification of https://leetcode.com/problems/balanced-binary-tree/discussion/comments/1570266
    public static boolean isBalancedRecursive(TreeNode root) {
        AtomicBoolean result = new AtomicBoolean(true);
        depth(root, result);
        return result.get();
    }

    private static int depth(TreeNode node, AtomicBoolean isBalanced) {
        if (node == null) return 0;

        int left = depth(node.left, isBalanced);
        int right = depth(node.right, isBalanced);

        if (Math.abs(left-right) > 1) isBalanced.set(false);

        return Math.max(left, right) + 1;
    }

    private static Map<TreeNode, Boolean> expectedResultsForIsBalanced() {
//        TreeNode leaf15 = new TreeNode(15);
//        TreeNode leaf7 = new TreeNode(7);
//        TreeNode leaf9 = new TreeNode(9);
//        TreeNode node20 = new TreeNode(20, leaf15, leaf7);
//        TreeNode node3 = new TreeNode(3, leaf9, node20);
//
//        return Map.of(node3, Boolean.TRUE);

        TreeNode leaf3 = new TreeNode(3);
        TreeNode node2 = new TreeNode(2, null, leaf3);
        TreeNode node1 = new TreeNode(1, null, node2);

        return Map.of(node1, Boolean.FALSE);
    }

    public static List<Integer> preorderTraversalRecursive(TreeNode root) {
        if (root == null) return Collections.emptyList();

        LinkedList<Integer> result = new LinkedList<>();
        result.add(root.val);
        result.addAll(preorderTraversalRecursive(root.left));
        result.addAll(preorderTraversalRecursive(root.right));

        return result;
    }

    public static List<Integer> preorderTraversalIterative(TreeNode root) {
        if (root == null)
            return Collections.emptyList();

        LinkedList<Integer> result = new LinkedList<>();
        ArrayDeque<TreeNode> queue = new ArrayDeque();
        queue.offer(root);

        while(! queue.isEmpty()) {
            TreeNode current = queue.poll();
            result.add(current.val);
            if (current.right != null)
                queue.offerFirst(current.right);
            if (current.left != null)
                queue.offerFirst(current.left);
        }

        return result;
    }

    public static List<Integer> postorderTraversal(TreeNode root) {
        if (root == null)
            return Collections.emptyList();

        LinkedList<Integer> result = new LinkedList<>();
        HashSet<TreeNode> visited = new HashSet<>();
        ArrayDeque<TreeNode> queue = new ArrayDeque();
        queue.offer(root);

        while (! queue.isEmpty()) {
            TreeNode current = queue.peek();
            if (current.left == null && current.right == null) {
                queue.poll();
                result.add(current.val);
                continue;
            }

            if (visited.contains(current)) {
                queue.poll();
                result.add(current.val);
                continue;
            }

            if (current.right != null)
                queue.offerFirst(current.right);

            if (current.left != null)
                queue.offerFirst(current.left);

            visited.add(current);
        }

        return result;
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
