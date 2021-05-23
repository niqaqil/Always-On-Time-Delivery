import java.util.ArrayList;
public class Test {
    
    public static void main(String[] args) {

        String s = "input.txt";
        InputData data = new InputData(s);
        Customer[] cus = new Customer[data.getN()];
        for (int i = 0; i < cus.length; i++) {
            cus[i] = new Customer(i, s);
        }

        System.out.println("Demand for each location");
        for (int i = 0; i < cus.length; i++) {
            System.out.println("Location " + i + ": " + cus[i].getDemand());
        }

        Vehicle car = new Vehicle(data.getC());
        System.out.println("Capacity of car: " + car.getCapacity());
        System.out.println();
        double[][] cost = new double[cus.length][cus.length];
        for (int i = 0; i < cost.length; i++) {
            for (int j = 0; j < cost[i].length; j++) {
                cost[i][j] = Customer.cost(cus[i].getCoordinate(), cus[j].getCoordinate());
            }
        }
        Customer.setCost(cost);
        //System.out.println("Cost from 0 to 1: " + Customer.cost(cus[0].getCoordinate(), cus[1].getCoordinate()));

        /*for (int i = 0; i < cost.length; i++) {
            System.out.printf("%d |\t", i);
            for (int j = 0; j < cost[i].length; j++) {
                System.out.printf("%.2f \t", cost[i][j]);
            }
            System.out.printf("|\n");
        }*/

        ArrayList<Integer> visited = new ArrayList<>();
        double totalCost = 0;
        System.out.println("\nGreedy Simulation");
        int count = 1;
        while (visited.size() != cus.length) {
            System.out.println("Vehicle " + count);
            totalCost += car.GreedySearch(cus, car, visited);
            count++;
        }
        System.out.println("Tour Cost = " + totalCost);
    }
    
}
