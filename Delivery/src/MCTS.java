import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;

public class MCTS {
    private double adjMatrix[][];
    private Depot d;
    private static ArrayList<Customer> c;
    private double distance;
    //LinkedList<MyCustomer> linkedList = new LinkedList<>();
    //ArrayList<Vehicle> vehicleList = new ArrayList<>();
    //StringBuilder sb;
    //double routeCost;
    //double tourCost;
    
    private int N;
    private double policy[][][];
    private double globalPolicy[][];
    ArrayList<Customer> location;
    Tour best_tour = new Tour(Double.POSITIVE_INFINITY);
    int timeLimit = 60;
    private final int l = 3;
    private final int ite = 100;
    
    public MCTS(ArrayList<Customer> c) {
        this.c = c;
        d=(Depot)c.get(0);
        N = c.size();
        this.policy = new double[l][N][N];
        this.globalPolicy = new double[N][N];
        adjMatrix = new double[c.size()][c.size()];  //store distance between every 2 nodes  customer and depot ,customer and customer
        //forming the graph
        for (int i = 0; i < c.size(); i++) { //edge
            for (int j = 0; j < c.size(); j++) {
                if(i==j) continue;
                if(adjMatrix[i][j]!=0)
                    continue; //because save before
                distance = Math.sqrt(Math.pow(c.get(i).xCoordinate-c.get(j).xCoordinate,2) + Math.pow(c.get(i).yCoordinate-c.get(j).yCoordinate,2));

                adjMatrix[i][j] = adjMatrix[j][i] = distance;  //because is undirected edge
            }
        }
        this.location = (ArrayList<Customer>) c.clone();
    }
    
    public Tour search(int level, int iteration) {
        Instant startTime = Instant.now();
        if(level==0){
            return rollout();
        }else{
            policy[level-1] = globalPolicy;
            for (int i = 0; i < iteration; i++) {
                Tour new_tour = search(level-1,iteration);
                if(new_tour.getTourCost() < best_tour.getTourCost()){
                    best_tour = new_tour;
                    adapt(best_tour,level);
                }
                if(Duration.between(startTime, Instant.now()).getSeconds() > timeLimit);
            }
            globalPolicy = policy[level-1];
        }
        return best_tour;
    }
    
    public void adapt(Tour a_tour, int level) {
        ArrayList<Customer> visited = new ArrayList<>();
        //for every route in tour
        for (int i = 0; i < a_tour.getRouteSize(); i++) {
            for (int j = 0; j < a_tour.getRoute().get(i).size()-1; j++) {
                int ALPHA = 1;
                policy[level-1][a_tour.getRoute().get(i).get(j).ID][a_tour.getRoute().get(i).get(j+1).ID] += ALPHA;
                double z = 0.0;
                //for every possible move that can be made by stop
                for (int k = 0; k < location.size(); k++) {
                    if(location.get(k).ID!=a_tour.getRoute().get(i).get(j).ID){
                        if(!visited.contains(location.get(k))){
                            z+= Math.exp(globalPolicy[a_tour.getRoute().get(i).get(j).ID][location.get(k).ID]);
                        }
                    }
                }
                //for every possible move that can be made by stop
                for (int k = 0; k < location.size(); k++) {
                    if(location.get(k).ID != a_tour.getRoute().get(i).get(j).ID){
                        if(!visited.contains(location.get(k))){
                            policy[level - 1][a_tour.getRoute().get(i).get(j).ID][location.get(k).ID] -= ALPHA * (Math.exp(globalPolicy[a_tour.getRoute().get(i).get(j).ID][location.get(k).ID]) / z);
                        }
                    }
                }
                visited.add(a_tour.getRoute().get(i).get(j));
            }
        }
    }
    
    public Tour rollout() {
        Customer currentStop;
        Customer nextStop;

        ArrayList<Customer> possible_successors = (ArrayList<Customer>)location.clone();
        possible_successors.remove(0); //depot removed

        ArrayList<Customer> visited = new ArrayList<>();
        ArrayList<Customer> checked = new ArrayList<>();

        Tour new_tour = new Tour(adjMatrix,c);
        new_tour.addNewRoute();

        int currentLoad = 0;

        while(true){
            currentStop = new_tour.getLastStop();
            for (int i = 0; i < possible_successors.size(); i++) {
                if(checked.contains(possible_successors.get(i)) || visited.contains(possible_successors.get(i))){
                    possible_successors.remove(i);
                }
            }
            //if no possible successor is available, return to depot
            if(possible_successors.isEmpty()){
                new_tour.addDepot(); //add depot
                //setRouteCost;
                //if all stops are visited
                if(checked.isEmpty()) {
                    //user for loop to compute Tour cost;
                    //new_tour.computeRouteCost(linkedList);
                    break; //rollout completed
                }
                //add new route into new tour
                new_tour.addNewRoute();
                currentLoad = 0;

                for (int i = 0; i < checked.size(); i++) {
                    possible_successors.add(checked.remove(i));
                }
                continue;      //skip to next loop to continue
            }
            nextStop = select_next_move(currentStop,possible_successors);

            if(currentLoad+nextStop.demandSize<=d.maximumCapacity){
                new_tour.addStop(nextStop);
                currentLoad += nextStop.demandSize;
                visited.add(nextStop);
            }else{
                checked.add(nextStop);
            }
        }
        return new_tour;
    }
    
    public Customer select_next_move(Customer currentStop, ArrayList<Customer> possible_successors) {
        double[] probability = new double[possible_successors.size()];
        double sum = 0;
        for (int i = 0; i < possible_successors.size(); i++) {
            probability[i] = Math.exp(globalPolicy[currentStop.ID][possible_successors.get(i).ID]);
            sum+=probability[i];
        }
        double mRand = new Random().nextDouble() * sum;
        int j = 0;
        sum = probability[0];
        while(sum<mRand){
            sum+=probability[++j];
        }
        return possible_successors.get(j);
    }
    
    public void displayTour() {
        System.out.println(search(l, ite));
    }

    @Override
    public String toString() {
        return best_tour.toString();
    }
}
