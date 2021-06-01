package GUI;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Set;

public class BestFirstSearch {

    private ArrayList<ArrayList<Integer>> paths = new ArrayList<>();
    private ArrayList<Double> pathCosts = new ArrayList<>();
    private ArrayList<Integer> routeCapacities = new ArrayList<>();

    public BestFirstSearch() {
        paths = new ArrayList<>();
        pathCosts = new ArrayList<>();
        routeCapacities = new ArrayList<>();
    }

    public void searchRoute(MyCustomer<Integer, Integer> cus, Vehicle car) {

        int numberOfCustomers = cus.getSize() - 1;
        //int vehicleMaxCapacity = data.getC();
        int vehicleMaxCapacity = car.getCapacity();
        int[][] coordinatesArray = new int[cus.getSize()][2];
        int[] demandArray = new int[cus.getSize()];
        for (int i = 0; i < demandArray.length; i++) {
            demandArray[i] = cus.getDemand(i);
            coordinatesArray[i][0] = cus.getX(i);
            coordinatesArray[i][1] = cus.getY(i);
        }
            
        Set<Integer> visitedPlaces = new TreeSet<>();
        visitedPlaces.add(0);
        int selectedNode = 0;
        int remainingCapacity = vehicleMaxCapacity;
        int routeCapacity = 0;
        double pathCost = 0.0;
        //ArrayList<ArrayList<Integer>> paths = new ArrayList<>();
        ArrayList<Integer> currentVehiclePath = new ArrayList<>();
        //ArrayList<Double> pathCosts = new ArrayList<>();
        //ArrayList<Integer> routeCapacities = new ArrayList<>();
        currentVehiclePath.add(0);
        paths.add(currentVehiclePath);

        while (visitedPlaces.size() < numberOfCustomers + 1) {
            double minCost = Double.MAX_VALUE;
            Integer bestDestination = null;
            for (int i = 1; i <= numberOfCustomers; i++) {
                if (selectedNode != i && !visitedPlaces.contains(i)) {
                    double cost = heuristicCost(selectedNode, i, coordinatesArray, remainingCapacity, demandArray[i]);
                    if (minCost > cost && demandArray[i] <= remainingCapacity) {
                        minCost = cost;
                        bestDestination = i;
                    }
                }
            }
            if (bestDestination == null) {
                remainingCapacity = vehicleMaxCapacity;
                pathCost += pathCost(selectedNode, 0, coordinatesArray);
                routeCapacities.add(routeCapacity);
                selectedNode = 0;
                currentVehiclePath.add(0);

                pathCosts.add(pathCost);
                pathCost = 0.0;
                routeCapacity = 0;

                currentVehiclePath = new ArrayList<>();
                currentVehiclePath.add(0);
                paths.add(currentVehiclePath);
            } else {
                pathCost += pathCost(selectedNode, bestDestination, coordinatesArray);
                routeCapacity += demandArray[bestDestination];

                selectedNode = bestDestination;
                visitedPlaces.add(bestDestination);
                remainingCapacity = remainingCapacity - demandArray[bestDestination];
                currentVehiclePath.add(bestDestination);
            }
        }
        currentVehiclePath.add(0);
        pathCost += pathCost(selectedNode, 0, coordinatesArray);
        pathCosts.add(pathCost);
        routeCapacities.add(routeCapacity);
    }

    private double heuristicCost(int from, int to, int[][] coordinates, int remainingCapacity, int demand) {
        double wasteWeight = 30;
        double euclideanDistance = pathCost(from, to, coordinates);
        int waste = remainingCapacity - demand;
        return euclideanDistance + (double) waste * wasteWeight;
    }

    private double pathCost(int from, int to, int[][] coordinates) {
        double x2 = Math.pow(coordinates[from][0] - coordinates[to][0], 2);
        double y2 = Math.pow(coordinates[from][1] - coordinates[to][1], 2);
        return Math.sqrt(x2 + y2);
    }

    public void printResults() {
        StringBuilder output = new StringBuilder();
        output.append("Best First Search Simulation\n");
        output.append("Tour\n");

        double tourCost = 0.0;
        for (Double cost : pathCosts) {
            tourCost += cost;
        }
        output.append("Tour Cost: " + tourCost + "\n");
        for (int i = 0; i < paths.size(); i++) {
            output.append("vehicle " + (i + 1) + "\n");
            output.append("Capacity: " + routeCapacities.get(i) + "\n");
            ArrayList<Integer> path = paths.get(i);
            for (Integer node : path) {
                output.append(node + " -> ");
            }
            output.delete(output.length() - 4, output.length());
            output.append("\nCost: " + pathCosts.get(i) + "\n");
        }
        System.out.println(output);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("~Best First Search Simulation~\n");
        output.append("Tour\n");

        double tourCost = 0.0;
        for (Double cost : pathCosts) {
            tourCost += cost;
        }
        output.append("Tour Cost: " + tourCost + "\n");
        for (int i = 0; i < paths.size(); i++) {
            output.append("Vehicle " + (i + 1) + "\n");
            output.append("Capacity: " + routeCapacities.get(i) + "\n");
            ArrayList<Integer> path = paths.get(i);
            for (Integer node : path) {
                output.append(node + " -> ");
            }
            output.delete(output.length() - 4, output.length());
            output.append("\nCost: " + pathCosts.get(i) + "\n");
        }
        return output.toString();
    }
}
