import java.util.LinkedList;

public class Vehicle {
    protected int capacity;
    private double cost;
    protected LinkedList<MyCustomer> path;

    public Vehicle(int capacity) {
        this.capacity = capacity;
    }

    public Vehicle(LinkedList<MyCustomer> path, double cost, int capacity) {
        this.capacity = capacity;
        this.path = (LinkedList<MyCustomer>) path.clone();
        this.cost = cost;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean allVisited(boolean[] visited) {
        for (boolean b : visited)
            if (!b) {
                return false;
            }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size()-1; i++) {
            sb.append(path.get(i).ID + " -> ");
        }
        sb.append(0);
        return sb + "\nCapacity: " + capacity + "\nCost: " + cost;
    }
    
    
}
