package GUI;

public class Customer<T extends Comparable<T>, N extends Comparable<N>> {

    private class Vertex<T extends Comparable<T>, N extends Comparable<N>> {
        T vertexInfo;
        int indeg;
        int outdeg;
        private int x;
        private int y;
        private int demand;
        Vertex<T, N> nextVertex;
        Edge<T, N> firstEdge;

        public Vertex() {
            vertexInfo = null;
            indeg = outdeg = 0;
            nextVertex = null;
            firstEdge = null;
        }

        public Vertex(T vInfo, Vertex<T, N> next, int X, int Y, int d) {
            vertexInfo = vInfo;
            indeg = outdeg = 0;
            x = X;
            y = Y;
            demand = d;
            nextVertex = next;
            firstEdge = null;
        }
    }

    private class Edge<T extends Comparable<T>, N extends Comparable<N>> {
        Vertex<T, N> toVertex;
        double weight;
        Edge<T, N> nextEdge;

        public Edge() {
            toVertex = null;
            weight = 0;
            nextEdge = null;
        }

        public Edge(Vertex<T, N> toVertex, double weight, Edge<T, N> nextEdge) {
            this.toVertex = toVertex;
            this.weight = weight;
            this.nextEdge = nextEdge;
        }
    }

    Vertex<T, N> head;
    private int size;
    
    // this for bfs
    protected int xCoordinate;
    protected int yCoordinate;
    protected int demandSize;
    protected boolean wasVisited;
    protected int ID;
    protected static int serialNum = 0;

    public Customer() {
        head = null;
        size = 0;
    }

    public Customer(int xCoordinate, int yCoordinate, int demandSize) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.demandSize = demandSize;
        wasVisited = false;
        this.ID = serialNum;
        serialNum++;
    }

    public int getSize() {
        return size;
    }

    public boolean hasCustomer(T v) { // is this customer in the graph?
        if (head == null)
            return false;
        Vertex<T, N> temp = head;
        while (temp != null) {
            if (temp.vertexInfo.compareTo(v) == 0)
                return true;
            temp = temp.nextVertex;
        }
        return false;
    }

    public boolean addCustomer(T v, int X, int Y, int d) {
        if (hasCustomer(v) == false) {  // The customer is not in the graph
            Vertex<T, N> temp = head;
            Vertex<T, N> newVertex = new Vertex<>(v, null, X, Y, d);
            if (head == null)  // Graph is empty, Point head to this vertex
                head = newVertex;
            else {
                Vertex<T, N> prev = head;
                while (temp != null) {  // Use prev to move to the last vertex
                    prev = temp;
                    temp = temp.nextVertex;
                }
                prev.nextVertex = newVertex;  // Add the vertex as last in the list
            }
            size++;
            return true;
        } else
            return false;  // Customer is already in the graph
    }

    public int getIndex(T v) {
        Vertex<T, N> temp = head;
        int pos = 0;
        while (temp != null) {  // loop to find the customer
            if (temp.vertexInfo.compareTo(v) == 0)  // customer is found
                return pos;
            temp = temp.nextVertex;  // move temp to the next vertex
            pos += 1;
        }
        return -1;
    }

    public boolean addEdge(T source, T destination, double w) {
        if (head == null)
            return false;
        if (!hasCustomer(source) || !hasCustomer(destination))
            return false;
        if (source == destination)
            return false;
        Vertex<T, N> sourceVertex = head;
        while (sourceVertex != null) {
            if (sourceVertex.vertexInfo.compareTo(source) == 0) {
                Vertex<T, N> destinationVertex = head;
                while (destinationVertex != null) {
                    if (destinationVertex.vertexInfo.compareTo(destination) == 0) {
                        Edge<T, N> currentEdge = sourceVertex.firstEdge;
                        Edge<T, N> newEdge = new Edge<>(destinationVertex, w, currentEdge);
                        sourceVertex.firstEdge = newEdge;
                        sourceVertex.outdeg++;
                        destinationVertex.indeg++;
                        return true;
                    }
                    destinationVertex = destinationVertex.nextVertex;
                }
            }
            sourceVertex = sourceVertex.nextVertex;
        }
        return false;
    }

    public T getCustomer(int pos) {  // return customer info
        if (pos > size - 1 || pos < 0)
            return null;
        Vertex<T, N> temp = head;
        for (int i = 0; i < pos; i++)
            temp = temp.nextVertex;
        return temp.vertexInfo;
    }

    public String getCoordinate(int pos) {  // return a string of coordinate (x, y)
        if (pos > size - 1 || pos < 0)
            return null;
        Vertex<T, N> temp = head;
        for (int i = 0; i < pos; i++)
            temp = temp.nextVertex;
        return " (" + temp.x + ", " + temp.y + ") ";
    }
    
    public int getX(int pos) {
        if (pos > size - 1 || pos < 0)
            return 0;
        Vertex<T, N> temp = head;
        for (int i = 0; i < pos; i++)
            temp = temp.nextVertex;
        return temp.x;
    }
    
    public int getY(int pos) {
        if (pos > size - 1 || pos < 0)
            return 0;
        Vertex<T, N> temp = head;
        for (int i = 0; i < pos; i++)
            temp = temp.nextVertex;
        return temp.y;
    }

    public int getDemand(int pos) {  // return demand at each location
        if (pos > size - 1 || pos < 0)
            return 0;
        Vertex<T, N> temp = head;
        for (int i = 0; i < pos; i++)
            temp = temp.nextVertex;
        return temp.demand;
    }
    
    public double calCost(int source, int dest) {  // calculate cost from source to destination
        Vertex<T, N> start = head;
        Vertex<T, N> end = head;
        for (int i = 0; i < source; i++)
            start = start.nextVertex;
        for (int i = 0; i < dest; i++)
            end = end.nextVertex;
        double sum = Math.pow(start.x - end.x, 2) + Math.pow(start.y - end.y, 2);
        return Math.sqrt(sum);
    }

    public void printEdges() {
        Vertex<T, N> temp = head;
        while (temp != null) {
            System.out.print("# " + temp.vertexInfo + " : ");
            Edge<T, N> currentEdge = temp.firstEdge;
            while (currentEdge != null) {
                System.out.print("[" + temp.vertexInfo + ","
                        + currentEdge.toVertex.vertexInfo + "] ");
                currentEdge = currentEdge.nextEdge;
            }
            System.out.println();
            temp = temp.nextVertex;
        }
    }

    public double getEdgeCost(T source, T destination) {
        N notFound = null;
        if (head == null)
            return 0;
        if (!hasCustomer(source) || !hasCustomer(destination))
            return 0;
        Vertex<T, N> sourceVertex = head;
        while (sourceVertex != null) {
            if (sourceVertex.vertexInfo.compareTo(source) == 0) {
                Edge<T, N> currentEdge = sourceVertex.firstEdge;
                while (currentEdge != null) {
                    if (currentEdge.toVertex.vertexInfo.compareTo(destination) == 0)
                        return currentEdge.weight;
                    currentEdge = currentEdge.nextEdge;
                }
            }
            sourceVertex = sourceVertex.nextVertex;
        }
        return 0;
    }
    
    public void reset() {
        head = null;
        size = 0;
    }
}
