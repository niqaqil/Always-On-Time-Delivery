package GUI;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BasicSearch {

    protected int numOfVehicles;
    protected double[][] matrixAdjacent;
    protected double distance;
    protected double route;
    protected double tour;
    protected Depot depot;
    protected List<Customer> customerList;
    protected LinkedList<Customer> linkedList = new LinkedList<>();
    protected ArrayList<Vehicle> vehiclePath = new ArrayList<>();

    public BasicSearch(List<Customer> list) {
        this.customerList = list;
        depot = (Depot) list.get(0);
        numOfVehicles = 0;
        matrixAdjacent = new double[list.size()][list.size()]; //distance between every 2 nodes
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i == j) {
                    continue;
                }
                if (matrixAdjacent[i][j] != 0) {
                    continue;
                }
                distance = Math.sqrt(Math.pow(list.get(i).xCoordinate - list.get(j).xCoordinate, 2) + Math.pow(list.get(i).yCoordinate - list.get(j).yCoordinate, 2));
                matrixAdjacent[i][j] = distance;
                matrixAdjacent[j][i] = distance;
            }
        }
    }

    public double calcRouteCost(LinkedList<Customer> linkedList) {
        double routeCost = 0;

        for (int i = 0; i < linkedList.size() - 1; i++) {
            int x = linkedList.get(i).ID;
            int y = linkedList.get(i + 1).ID;
            routeCost += matrixAdjacent[x][y];
        }
        return routeCost;
    }

    public boolean allVisited(boolean[][] visitedArray) {
        for (int i = 0; i < visitedArray.length; i++) {
            for (int j = 0; j < visitedArray[0].length; j++) {
                if (!visitedArray[i][j]) //if some node is not visited yet
                {
                    return false;
                }
            }
        }
        return true;
    }

    public void searchRoute() {
        int num = customerList.size() - 1;
        List<Integer> routeComb[] = new LinkedList[num];  //adjacency list for different route combination
        double[] cost = new double[num];      //cost of the routeComb[i] route
        int[] totalSize = new int[num];

        boolean[][] visited = new boolean[num][num];
        //store visited customer for every customer

        for (int i = 0; i < routeComb.length; i++) {
            routeComb[i] = new LinkedList<>(); //new linkedList that stores the customer's neighbors
            routeComb[i].add(0);   //start with depot
        }

        for (int i = 0; i < routeComb.length; i++) {
            routeComb[i].add(i + 1);  //add every possible customer
            visited[i][i] = true;  //first node visited
            cost[i] += matrixAdjacent[0][i + 1];  //depot to first customer
            totalSize[i] += customerList.get(i + 1).demandSize; //adding customer's demandSize to totalSize
        }

        while (!allVisited(visited)) { //while all nodes not visited yet
            int lastEle;

            for (int i = 0; i < routeComb.length; i++) {
                lastEle = routeComb[i].get(routeComb[i].size() - 1);  //get last element of list. We building from behind
                int nodes = 0;
                double min = Double.POSITIVE_INFINITY;
                for (int j = 1; j < matrixAdjacent.length; j++) {  //check distance to adjacent node of customer i
                    if (visited[i][j - 1]) //already visited node in the list.
                    {
                        continue;
                    }
                    if (matrixAdjacent[lastEle][j] < min && totalSize[i] + customerList.get(j).demandSize <= depot.maximumCapacity) { //of the cost and demand size is lesser
                        min = matrixAdjacent[lastEle][j];  //update the route cost with cheapest cost
                        nodes = j; //update the node ID
                    }

                }
                if (nodes != 0) {  //there exists a customer demand size that still can be add to the vehicle
                    visited[i][nodes - 1] = true;
                    cost[i] += min;
                    routeComb[i].add(nodes);
                    totalSize[i] += customerList.get(nodes).demandSize;
                } else {  //no customer demand size can fit in the vehicle anymore, need new vehicle
                    totalSize[i] = 0;
                    cost[i] += matrixAdjacent[routeComb[i].get(routeComb[i].size() - 1)][0]; //go back to depot
                    routeComb[i].add(0);   //act as ending for previous route and also starting of next vehicle

                }
            }
            if (allVisited(visited)) {  //since every customer visited,return to depot
                for (int i = 0; i < routeComb.length; i++) {
                    cost[i] += matrixAdjacent[routeComb[i].get(routeComb[i].size() - 1)][0]; //e.g: matrixAdjacent[last node of the routeComb[0]][0]
                    routeComb[i].add(0);
                }
                break;
            }
        }

        double minCost = Double.POSITIVE_INFINITY;
        int minIndex = 0;
        for (int i = 0; i < routeComb.length; i++) {
            if (cost[i] < minCost) {
                minCost = cost[i];
                minIndex = i;
            }
        }
        tour = minCost;
        //System.out.println("🚚 ~ Basic Simulation Tour ~ 🚚" + "\nTour Cost: " + minCost+ "\n===============================\n");
        int index = 1;
        linkedList.clear();
        int currentLoad = 0;
        linkedList.add(customerList.get(0));
        for (int i = index; i < routeComb[minIndex].size(); i++) {  //separate the minCost path into different vehicle
            if (routeComb[minIndex].get(i) != 0) {
                linkedList.add(customerList.get(routeComb[minIndex].get(i)));
                currentLoad += customerList.get(routeComb[minIndex].get(i)).demandSize;
                continue;
            }
            index = i;
            linkedList.add(customerList.get(0));
            route = calcRouteCost(linkedList);
            vehiclePath.add(new Vehicle(linkedList, route, currentLoad));
            linkedList.clear();
            currentLoad = 0;
            linkedList.add(customerList.get(0));
        }
    }

    public String displayPath(ArrayList<Vehicle> vehicleList) {
        String s = "";
        for (int i = 1; i <= vehicleList.size(); i++) {
            s += "Vehicle " + i + "\n";
            s += vehicleList.get(i - 1) + "\n";
        }
        return s;
    }

    @Override
    public String toString() {
        String s = "~Basic Simulation~\n";
        s += "Tour\nTour Cost: " + tour + "\n";
        s += displayPath(vehiclePath);
        return s;
    }
}
