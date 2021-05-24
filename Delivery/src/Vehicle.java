import java.util.ArrayList;

public class Vehicle {
    private int capacity;
    protected static ArrayList<int[]> greedyRoute; // save arrays of routes for each car
    protected static ArrayList<Double> cost; // save cost for each car
    protected static ArrayList<Integer> capacities; // save capacities for each car
    private double costs; // Tour cost

    public Vehicle(int capacity) {
        greedyRoute = new ArrayList<>();
        cost = new ArrayList<>();
        capacities = new ArrayList<>();
        this.capacity = capacity;
        costs = 0;
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



    public int greedySearch(Customer[] cus, Vehicle car) {
        ArrayList<Integer> visited = new ArrayList<>();
        double totalCost = 0;
        //System.out.println("Greedy Simulation");
        int count = 1;
        while (visited.size() != cus.length) {
            //System.out.println("Vehicle " + count);
            double costRoute = car.vehicleRoute(cus, car, visited);
            cost.add(costRoute);
            totalCost += costRoute;
            count++;
        }
        //System.out.println("Tour Cost = " + totalCost);
        setCost(totalCost);
        return count;
    }

    public double vehicleRoute(Customer[] cus, Vehicle car, ArrayList<Integer> visitVertex) {
        int totalDemand = 0;
        double[][] cost = Customer.getCost();
        double totalCost = 0;
        boolean[] visited = new boolean[cus.length];
        int parent = 0;
        visited[parent] = true;
        for (Integer vertex : visitVertex) visited[vertex] = true;
        if (!visitVertex.contains(parent))
            visitVertex.add(parent);
        ArrayList<Integer> route = new ArrayList<Integer>();
        route.add(parent);
        /*for (int i = 0; i < visited.length; i++)
            System.out.print(visited[i] + " ");*/
        int nextVertex = -1;
        while (!allVisited(visited) && totalDemand <= car.getCapacity()) {
            nextVertex = shortPath(cost, parent, visited);
            visited[nextVertex] = true;
            while (totalDemand + cus[nextVertex].getDemand() > car.getCapacity()) {
                nextVertex = shortPath(cost, parent, visited);
                visited[nextVertex] = true;
            }
            if (!visitVertex.contains(nextVertex))
                visitVertex.add(nextVertex);
            route.add(nextVertex);
            totalDemand += cus[nextVertex].getDemand();
            totalCost += cost[parent][nextVertex];
            parent = nextVertex;
        }

        if (visitVertex.size() == cus.length) {
            totalCost += cost[nextVertex][0];
            route.add(0);
        }
        //System.out.println();
        //System.out.println("Capacity: " + totalDemand);
        //System.out.println("Cost: " + totalCost);
        int[] vehicleRoute = new int[route.size()];
        for (int i = 0; i < route.size(); i++) {
            vehicleRoute[i] = route.get(i);
        }
        capacities.add(totalDemand);
        addGreedyRoute(vehicleRoute);
        return totalCost;
    }

    public int shortPath(double[][] cost, int parent, boolean[] visit) {
        double min = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < cost[parent].length; i++) {
            if (i != parent)
                if (cost[parent][i] < min && !visit[i]) {
                    min = cost[parent][i];
                    index = i;
                }
        }
        return index;
    }

    public boolean allVisited(boolean[] visited) {
        for (boolean b : visited)
            if (!b) {
                return false;
            }
        return true;
    }

    public void printSimulation() {
        System.out.println("Tour");
        System.out.println("Tour Cost: " + costs);
        for (int i = 0; i < greedyRoute.size(); i++) {
            System.out.println("Vehicle " + i+1);
            for (int j = 0; j < greedyRoute.get(i).length; j++) {
                System.out.print(greedyRoute.get(i)[j]);
                if (j != greedyRoute.get(i).length-1)
                    System.out.print(" -> ");
            }
            System.out.println("\nCapacity: " + capacities.get(i));
            System.out.println("Cost: " + cost.get(i));
        }
    }
}
