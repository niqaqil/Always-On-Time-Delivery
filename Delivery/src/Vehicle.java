import java.util.ArrayList;
import java.util.LinkedList;

public class Vehicle {
    protected int capacity;
    protected static ArrayList<int[]> greedyRoute; // save arrays of routes for each car
    protected static ArrayList<Double> cost; // save cost for each car
    protected static ArrayList<Integer> capacities; // save capacities for each car
    protected LinkedList<Customer> bfsPath;
    private double costs; // Tour cost

    public Vehicle(int capacity) {
        greedyRoute = new ArrayList<>();
        cost = new ArrayList<>();
        capacities = new ArrayList<>();
        this.capacity = capacity;
        costs = 0;
    }

    public Vehicle(LinkedList<Customer> bfsPath, int capacity, double costs) {
        this.capacity = capacity;
        this.bfsPath = bfsPath;
        this.costs = costs;
    }

    public int getCapacity() {
        return capacity;
    }

    public static void addGreedyRoute(int[] r) {
        greedyRoute.add(r);
    }

    public double getCost() {
        return costs;
    }

    public void setCost(double cost) {
        this.costs = cost;
    }

    public boolean allVisited(boolean[] visited) {
        for (boolean b : visited)
            if (!b) {
                return false;
            }
        return true;
    }

    public void greedySearch(MyCustomer<Integer, Integer> cus, Vehicle car) {
        ArrayList<Integer> visited = new ArrayList<>();
        double totalCost = 0;
        int count = 1;
        while (visited.size() != cus.getSize()) {
            double costRoute = car.vehicleRoute(cus, car, visited);
            cost.add(costRoute);
            totalCost += costRoute;
            count++;
        }
        setCost(totalCost);
    }

    public double vehicleRoute(MyCustomer<Integer, Integer> cus, Vehicle car, ArrayList<Integer> visitVertex) {
        int totalDemand = 0;
        double totalCost = 0;
        boolean[] visited = new boolean[cus.getSize()];
        int parent = 0;
        if (!visitVertex.contains(parent))
            visitVertex.add(parent);
        visited[parent] = true;
        for (Integer vertex : visitVertex) visited[vertex] = true;
        ArrayList<Integer> route = new ArrayList<Integer>();
        route.add(parent);
        int nextVertex = -1;
        while (!allVisited(visited) && totalDemand <= car.getCapacity()) {
            nextVertex = shortPath(parent, cus, visited);
            visited[nextVertex] = true;
            while (totalDemand + cus.getDemand(nextVertex) > car.getCapacity()) {
                nextVertex = shortPath(parent, cus, visited);
                visited[nextVertex] = true;
            }
            if (!visitVertex.contains(nextVertex))
                visitVertex.add(nextVertex);
            route.add(nextVertex);
            totalDemand += cus.getDemand(nextVertex);
            totalCost += cus.getEdgeCost(parent, nextVertex);
            parent = nextVertex;
        }
        if (visitVertex.size() == cus.getSize() || totalDemand == car.getCapacity()) {
            totalCost += cus.getEdgeCost(nextVertex, 0);
            route.add(0);
        }
        int[] vehicleRoute = new int[route.size()];
        for (int i = 0; i < route.size(); i++) {
            vehicleRoute[i] = route.get(i);
        }
        capacities.add(totalDemand);
        addGreedyRoute(vehicleRoute);
        return totalCost;
    }

    private int shortPath(int parent, MyCustomer<Integer, Integer> cus, boolean[] visit) {
        double min = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < cus.getSize(); i++) {
            if (i != parent)
                if (cus.getEdgeCost(parent, i) < min && !visit[i]) {
                    min = cus.getEdgeCost(parent, i);
                    index = i;
                }
        }
        return index;
    }

    public void printSimulation() {
        System.out.println("Tour");
        System.out.println("Tour Cost: " + costs);
        for (int i = 0; i < greedyRoute.size(); i++) {
            System.out.println("Vehicle " + (i + 1));
            for (int j = 0; j < greedyRoute.get(i).length; j++) {
                System.out.print(greedyRoute.get(i)[j]);
                if (j != greedyRoute.get(i).length - 1)
                    System.out.print(" -> ");
            }
            System.out.println("\nCapacity: " + capacities.get(i));
            System.out.println("Cost: " + cost.get(i));
        }
    }
}
