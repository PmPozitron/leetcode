import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class InorderTreeTraversal {
    public static void main(String[] args) {
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

        Map.of(
                sixteen, List.of(7,1,5,3,4,2,9),
//                nine, List.of(1,3,2)
//                        one, List.of(1, 3, 2)
//                        three, List.of(1, 3, 2)
//                        four, List.of(1, 2, 3)
//                        eight, List.of(2, 4, 1, 3)
                        new TreeNode(), Collections.emptyList(),
                        new TreeNode(1), List.of(1)
                ).entrySet().stream()
                .forEach(item -> System.out.printf("for %s expected result is %s and actual is %s\n", item.getKey(), item.getValue(), inorderTraversal(item.getKey())));
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
