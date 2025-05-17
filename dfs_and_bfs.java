
import java.util.*;

class TreeNode {
    int val;
    List<TreeNode> children;

    public TreeNode(int val) {
        this.val = val;
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }
}

public class dfs_and_bfs{
    List<Integer> dfsPath = new ArrayList<>();
    List<Integer> bfsPath = new ArrayList<>();

    // DFS traversal to store the path
    public void dfs(TreeNode node) {
        if (node == null)
            return;

        dfsPath.add(node.val); // Store node in DFS path

        for (TreeNode child : node.children) {
            dfs(child);
        }
    }

    public void bfs(TreeNode root) {
        if (root == null)
            return;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            bfsPath.add(node.val); // Store node in BFS path

            for (TreeNode child : node.children)
                queue.add(child);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, TreeNode> nodes = new HashMap<>();

        System.out.println("Enter number of nodes:");
        int n = scanner.nextInt();

        System.out.println("Enter " + (n - 1) + " edges (parent child):");
        for (int i = 0; i < n; i++) {
            nodes.put(i, new TreeNode(i)); // Create all nodes
        }

        for (int i = 0; i < n - 1; i++) {
            int parent = scanner.nextInt();
            int child = scanner.nextInt();
            nodes.get(parent).addChild(nodes.get(child));
        }

        TreeNode root = nodes.get(0); // Assuming 0 as the root node

        System.out.println("\nTree Structure:");
        for (Map.Entry<Integer, TreeNode> entry : nodes.entrySet()) {
            TreeNode node = entry.getValue();
            if (!node.children.isEmpty()) {
                System.out.print(node.val + " -> ");
                for (int i = 0; i < node.children.size(); i++) {
                    System.out.print(node.children.get(i).val);
                    if (i < node.children.size() - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }
        }

        dfs_and_bfs tree = new dfs_and_bfs();
        tree.dfs(root);
        System.out.println("\nDFS Traversal Path: " + tree.dfsPath);

        tree.bfs(root);
        System.out.println("\nBFS Traversal Path: " + tree.bfsPath);

        scanner.close();
    }
}