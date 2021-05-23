
public class Customer {
    private int demand;
    static String s;
    private int x;
    private int y;
    protected static int[][] location;
    protected static int[] demands;
    protected static double[][] cost;

    public Customer(int i, String input) {
        s = input;
        InputData data = new InputData(s);
        demands = data.getDemand();
        location = data.getCoordinate();
        demand = demands[i];
        x = location[i][0];
        y = location[i][1];
    }

    public int getDemand() {
        return demand;
    }

    public int[] getCoordinate() {
        return new int[]{x, y};
    }

    public static double cost(int[] start, int[] end) {
        double sum = Math.pow(start[0] - end[0], 2) + Math.pow(start[1] - end[1], 2);
        return Math.sqrt(sum);
    }

    public static double[][] getCost() {
        return cost;
    }

    public static void setCost(double[][] cost) {
        Customer.cost = cost;
    }
}
