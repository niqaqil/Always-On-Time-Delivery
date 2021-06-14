package GUI;

import java.util.LinkedList;

public class Vehicle {

    protected int capacity;
    private double cost;
    private LinkedList<Customer> path;

    public Vehicle(int capacity) {
        this.capacity = capacity;
    }

    public Vehicle(LinkedList<Customer> path, double cost, int capacity) {
        this.capacity = capacity;
        this.path = (LinkedList<Customer>) path.clone();
        this.cost = cost;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size() - 1; i++) {
            sb.append(path.get(i).ID + " -> ");
        }
        sb.append(0);
        return sb + "\nCapacity: " + capacity + "\nCost: " + cost;
    }

}
