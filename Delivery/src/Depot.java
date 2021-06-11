public class Depot extends MyCustomer{
    protected int numOfCustomers;
    protected int maximumCapacity;

    Depot(int numOfCustomers, int maxCapacity, int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate, maxCapacity);
        this.numOfCustomers = numOfCustomers;
        this.maximumCapacity=super.demandSize;
    }

    
}
