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
