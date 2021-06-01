import java.util.LinkedList;

public class Vehicle {
    protected int capacity;
    protected LinkedList<MyCustomer> bfsPath;

    public Vehicle(int capacity) {
        this.capacity = capacity;
    }

    public Vehicle(LinkedList<MyCustomer> bfsPath, int capacity, double costs) {
        this.capacity = capacity;
        this.bfsPath = bfsPath;
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
}
