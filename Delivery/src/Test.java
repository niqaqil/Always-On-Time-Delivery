public class Test {

    public static void main(String[] args) {
        String s = "input.txt";
        InputData data = new InputData(s);
        int[][] loc = data.getCoordinate(); // coordinate for each customer include depot
        int[] demand = data.getDemand();
        Customer[] cus = new Customer[data.getN()];
         MyCustomer<Integer, Integer> customer = new MyCustomer<>(); // class graph
        for (int i = 0; i < cus.length; i++) {
            cus[i] = new Customer(i, s);
            customer.addCustomer(i, loc[i][0], loc[i][1], demand[i]);
        }
        
        System.out.println("Number of Customer: " + (customer.getSize()-1));
        System.out.println("Customer and their demand");
        for (int i = 0; i < customer.getSize(); i++) {
            if (i == 0)
                System.out.println("Depot" + customer.getCoordinate(i) + ": " + customer.getDemand(i));
            else
                System.out.println("Customer " + i + customer.getCoordinate(i) + ": "  + customer.getDemand(i));
        }

        Vehicle car = new Vehicle(data.getC());
        System.out.println("Capacity of car: " + car.getCapacity());
        System.out.println();
        double[][] cost = new double[cus.length][cus.length];
        for (int i = 0; i < cost.length; i++) {
            for (int j = 0; j < cost[i].length; j++) {
                cost[i][j] = Customer.cost(cus[i].getCoordinate(), cus[j].getCoordinate());
                customer.addEdge(i, j, cost[i][j]);
            }
        }
        customer.printEdges(); // print edges for each location
        System.out.println("");
        Customer.setCost(cost);
        //System.out.println("Cost from 0 to 1: " + Customer.cost(cus[0].getCoordinate(), cus[1].getCoordinate()));

        /*for (int i = 0; i < cost.length; i++) {
            System.out.printf("%d |\t", i);
            for (int j = 0; j < cost[i].length; j++) {
                System.out.printf("%.2f \t", cost[i][j]);
            }
            System.out.printf("|\n");
        }*/
        int totalVehicle = car.greedySearch(cus, car);
        System.out.println("Greedy Simulation");
        car.printSimulation();
        /*for (int i = 0; i < Vehicle.carsRoute.size(); i++) {
            for (int j = 0; j < Vehicle.carsRoute.get(i).length; j++)
                System.out.print(Vehicle.carsRoute.get(i)[j] + " ");
            System.out.println();
        }*/
    }
}
