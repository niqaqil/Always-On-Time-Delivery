package GUI;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Tour {
    private double tourCost;
    private ArrayList<LinkedList<Customer>> route = new ArrayList<>();
    double[][] adjMatrix;
    List<Customer> cus;

    public Tour(double tourCost) {
        this.tourCost = tourCost;
    }

    public Tour(double[][] adjMatrix, List<Customer> cus) {
        this.adjMatrix = adjMatrix;
        this.cus = cus;
        tourCost = 0;
    }

    public void add(LinkedList<Customer> r) {
        route.add(r);
    }

    public ArrayList<LinkedList<Customer>> getRoute() {
        return route;
    }

    public double computeTourCost() {
        tourCost = 0;
        for (int i = 0; i < route.size(); i++)
            for (int j = 0; j < route.get(i).size()-1; j++) {
                int firstPoint = route.get(i).get(j).ID;
                int secondPoint = route.get(i).get(j+1).ID;
                tourCost += adjMatrix[firstPoint][secondPoint];
            }
        return tourCost;
    }

    public double computeRouteCost(LinkedList<Customer> c) {
        double routeCost = 0;
        int i;
        for (i = 0; i < c.size()-1; i++) {
            int firstPoint = c.get(i).ID;
            int secondPoint = c.get(i+1).ID;
            routeCost += adjMatrix[firstPoint][secondPoint];
        }
        return routeCost;
    }

    public void addStop(Customer n) {
        route.get(route.size()-1).add(n);
        tourCost = computeTourCost();
    }

    public void addNewRoute() {
        LinkedList<Customer> tempList = new LinkedList<>();
        tempList.add(cus.get(0));
        route.add(tempList);
        tourCost = computeTourCost();
    }

    public Customer getLastStop() {
        int routeSize = route.size()-1;
        int linkedListRouteSize = route.get(routeSize).size()-1;
        return route.get(routeSize).get(linkedListRouteSize);
    }

    public int computeCapacity(LinkedList<Customer> c) {
        int capacity = 0;
        for (int i = 1; i < c.size()-1; i++)
            capacity += c.get(i).demandSize;
        return capacity;
    }

    public double getTourCost() {
        return tourCost;
    }

    public void setTourCost(double tourCost) {
        this.tourCost = tourCost;
    }

    public int getRouteSize() {
        return route.size();
    }

    public void addDepot() {
        route.get(getRouteSize()-1).add(cus.get(0));
        tourCost = computeTourCost();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("~MCTS Simulation~\nTour Cost: " + tourCost);
        for (int i = 0; i < route.size(); i++) {
            sb.append("\nVehicle " + (i+1) + "\n");
            for (int j = 0; j < route.get(i).size()-1; j++) {
                sb.append(route.get(i).get(j).ID + " -> ");
            }
            sb.append("0\n");
            sb.append("Capacity: " + computeCapacity(route.get(i)) +"\nCost: " + computeRouteCost(route.get(i)));
        }
        return sb.toString();
    }
}
