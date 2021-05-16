
public class Test {
    
    public static void main(String[] args) {

        InputData data = new InputData("input1.txt");
        System.out.print(data.getN() + " ");
        System.out.println(data.getC());
        int[][] location = data.getCoordinate();
        int[] demand = data.getDemand();
        for (int i = 0; i < location.length; i++) {
            for (int j = 0; j < location[i].length; j++)
                System.out.print(location[i][j] + " ");
            System.out.println("" + demand[i]);
        }

        System.out.println("\nEuclidean distance travelled by the vehicle....");
        double[][] matrix = new double[data.getN()][data.getN()];
        for (int i = 0; i < location.length; i++) {
            for (int j = 1; j < location.length; j++) {
                if (i >= j)
                    continue;
                double sum = Math.pow(location[i][0]-location[j][0],2) + Math.pow(location[i][1]-location[j][1],2);
                double num = Math.sqrt(sum);
                matrix[i][j] = num;
                matrix[j][i] = num;
                System.out.println("Cost " + i + " to " + j + ": " + num);
            }
        }
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("%d |\t", i);
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%.2f \t", matrix[i][j]);
            }
            System.out.printf("|\n");
        }
        System.out.println("Testing");
    }
    
}
