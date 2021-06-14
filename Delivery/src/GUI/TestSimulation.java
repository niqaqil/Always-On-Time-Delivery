package GUI;

import java.util.ArrayList;
import java.util.List;

public class TestSimulation {

    public static void main(String[] args) {
        String s = "n5-c10.txt";
        InputData data = new InputData(s);
        int[][] loc = data.getCoordinate(); // coordinate for each customer include depot
        int[] demand = data.getDemand();
        Customer<Integer, Integer> customer = new Customer<>(); // class graph
        for (int i = 0; i < data.getN(); i++) {
            customer.addCustomer(i, loc[i][0], loc[i][1], demand[i]);
        }

        System.out.println("Number of Customer(s): " + (customer.getSize() - 1));
        System.out.println("Customer and their demand");
        for (int i = 0; i < customer.getSize(); i++) {
            if (i == 0) {
                System.out.println("Depot" + customer.getCoordinate(i) + ": " + customer.getDemand(i));
            } else {
                System.out.println("Customer " + i + customer.getCoordinate(i) + ": " + customer.getDemand(i));
            }
        }

        Vehicle car = new Vehicle(data.getC());
        System.out.println("Capacity of each vehicles: " + car.getCapacity());
        System.out.println();
        double[][] cost = new double[customer.getSize()][customer.getSize()];
        for (int i = 0; i < cost.length; i++) {
            for (int j = 0; j < cost[i].length; j++) {
                cost[i][j] = customer.calCost(i, j);
                customer.addEdge(i, j, cost[i][j]);
            }
        }

        List<Customer> nodeList = new ArrayList<>();
        nodeList.add(new Depot(data.getN(), data.getC(), loc[0][0], loc[0][1]));
        for (int i = 1; i < loc.length; i++) {
            nodeList.add(new Customer(loc[i][0], loc[i][1], demand[i]));
        }

        // Basic Simulation
        BasicSearch bfs = new BasicSearch(nodeList);
        bfs.searchRoute();
        System.out.println("\n" + bfs.toString());

        // Greedy Simulation
        GreedySearch greedy = new GreedySearch();
        greedy.searchRoute(customer, car);
        System.out.println(greedy.toString());

        // MCTS Simulation
        MCTS mcts = new MCTS((ArrayList<Customer>) nodeList);
        mcts.search(3, 100);
        System.out.println(mcts.toString());

        // Best First Search Simulation
        BestFirstSearch best = new BestFirstSearch();
        best.searchRoute(data, customer, car);
        System.out.println("\n" + best.toString());
    }
}
