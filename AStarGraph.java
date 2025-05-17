import java.util.*;

class Edge {
    int target;
    int weight;

    Edge(int target, int weight) {
        this.target = target;
        this.weight = weight;
    }
}

class Node implements Comparable<Node> {
    int id;
    int cost;

    Node(int id, int cost) {
        this.id = id;
        this.cost = cost;
    }


    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.cost, other.cost);
    }
}

public class AStarGraph {

    private static void addEdge(List<List<Edge>> graph, int source, int target, int weight) {
        graph.get(source).add(new Edge(target, weight));
        graph.get(target).add(new Edge(source, weight)); // For undirected graph
    }

    private static void displayGraph(List<List<Edge>> graph) {
        for (int i = 0; i < graph.size(); i++) {
            System.out.print(i + " -> ");
            for (Edge edge : graph.get(i)) {
                System.out.print("[" + edge.target + ", " + edge.weight + "] ");
            }
            System.out.println();
        }
    }

    public static List<Integer> aStar(List<List<Edge>> graph, int[] heuristic, int start, int goal, int nodes) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        boolean[] closedSet = new boolean[nodes];
        int[] gScore = new int[nodes];
        int[] cameFrom = new int[nodes];

        Arrays.fill(gScore, Integer.MAX_VALUE);
        Arrays.fill(cameFrom, -1);

        gScore[start] = 0;
        openSet.add(new Node(start, heuristic[start]));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.id == goal) {
                return reconstructPath(cameFrom, start, goal);
            }

            if (closedSet[current.id]) continue;
            closedSet[current.id] = true;

            for (Edge edge : graph.get(current.id)) {
                int neighbor = edge.target;
                int tentativeGScore = gScore[current.id] + edge.weight;

                if (tentativeGScore < gScore[neighbor]) {
                    gScore[neighbor] = tentativeGScore;
                    cameFrom[neighbor] = current.id;
                    int fScore = gScore[neighbor] + heuristic[neighbor];
                    openSet.add(new Node(neighbor, fScore));
                }
            }
        }
        return Collections.emptyList();
    }

    private static List<Integer> reconstructPath(int[] cameFrom, int start, int goal) {
        List<Integer> path = new ArrayList<>();
        for (int at = goal; at != -1; at = cameFrom[at]) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of nodes: ");
        int nodes = scanner.nextInt();

        if(nodes <=1) {
            System.out.println("Enter more than 1 node :");
            nodes = scanner.nextInt();
        }
        System.out.print("Enter the number of edges: ");
        int edges = scanner.nextInt();
        while(true){
            if((edges > nodes*(nodes-1)/2 ) || edges < nodes-1) {
                System.out.println("The maximum number of edges are " + nodes*(nodes-1)/2 +" and the minimum numnber of edges are "+ (nodes-1) +"." );
                System.out.println("Enter the number of edges again in the range");
                edges = scanner.nextInt();
                continue;
            }
            else
                break;
        }

        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < nodes; i++) {
            graph.add(new ArrayList<>());
        }

        System.out.println("Enter the edges between the nodes. (source target weight):");
        for (int i = 0; i < edges; i++) {
            int source = scanner.nextInt();
            int target = scanner.nextInt();
            int weight = scanner.nextInt();
            if(weight<=0){
                System.out.println("Weight of an edge can't be zero or less that zero");
                i--;
                continue;
            }
            addEdge(graph, source, target, weight);
        }

        int[] heuristic = new int[nodes];
        System.out.println("Enter heuristic values for each node:");
        for (int i = 0; i < nodes; i++) {
            System.out.print("Heuristic for node " + i + ": ");
            heuristic[i] = scanner.nextInt();
        }

        System.out.print("Enter the start node: ");
        int start = scanner.nextInt();

        System.out.print("Enter the goal node: ");
        int goal = scanner.nextInt();

        System.out.println("\nGraph Representation:");
        displayGraph(graph);

        System.out.println("\nFinding shortest path using A* algorithm...");
        List<Integer> path = aStar(graph, heuristic, start, goal, nodes);

        if (path.isEmpty()) {
            System.out.println("No path found!");
        } else {
            System.out.println("Shortest path: " + path);
        }
        scanner.close();
    }
}