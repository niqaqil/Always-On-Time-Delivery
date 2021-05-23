import java.util.ArrayList;

public class Vehicle {
    private int capacity;

    public Vehicle(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public double GreedySearch(Customer[] cus, Vehicle car, ArrayList<Integer> visitVertex) {
        int totalDemand = 0;
        double[][] cost = Customer.getCost();
        double totalCost = 0;
        boolean[] visited = new boolean[cus.length];
        int parent = 0;
        visited[parent] = true;
        for (Integer vertex : visitVertex) visited[vertex] = true;
        if (!visitVertex.contains(parent))
            visitVertex.add(parent);
        /*for (int i = 0; i < visited.length; i++)
            System.out.print(visited[i] + " ");*/
        System.out.print(parent + " -> ");
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
            if (nextVertex == 0)
                System.out.print(" 0 ");
            else
                System.out.print(nextVertex + " -> ");
            totalDemand += cus[nextVertex].getDemand();
            totalCost += cost[parent][nextVertex];
            parent = nextVertex;
        }

        if (visitVertex.size() == cus.length) {
            totalCost += cost[nextVertex][0];
            System.out.print(" 0 ");
        }
        System.out.println();
        System.out.println("Capacity: " + totalDemand);
        System.out.println("Cost: " + totalCost);
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
}
