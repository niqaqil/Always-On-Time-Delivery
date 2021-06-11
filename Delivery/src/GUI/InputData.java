package GUI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputData {
    private String s;

    public InputData(String s) {
        this.s = s;
    }

    public int getN() {  // get number of customer
        int N = 0;
        try {
            Scanner in = new Scanner(new FileInputStream(s));
            N = in.nextInt();
            in.close();
        }catch (FileNotFoundException e) {
            System.out.println("File wos not found");
        }
        return N;
    }

    public int getC() {  // get maximum capacity of vehicles
        String[] C = {};
        try {
            Scanner in = new Scanner(new FileInputStream(s));
            C = in.nextLine().split(" ");
            in.close();
        }catch (FileNotFoundException e) {
            System.out.println("File wos not found");
        }
        return Integer.parseInt(C[1]);
    }

    public int[][] getCoordinate() {  // get coordinate of each locations include depot
        String[] data = new String[getN()+1];
        try {
            Scanner in = new Scanner(new FileInputStream(s));
            int i = 0;
            while (in.hasNextLine()) {
                data[i] = in.nextLine();
                i++;
            }
            in.close();
        }catch (FileNotFoundException e) {
            System.out.println("File wos not found");
        }

        int[][] location = new int[getN()][2];
        for (int j = 1; j < data.length; j++) {
            String[] temp = data[j].split(" ");
            for (int k = 0; k < 2; k++) {
                int num = Integer.parseInt(temp[k]);
                location[j-1][k] = num;
            }
        }
        return location;
    }

    public int[] getDemand() {  // get demand size of each locations include depot
        String[] data = new String[getN()+1];
        try {
            Scanner in = new Scanner(new FileInputStream(s));
            int i = 0;
            while (in.hasNextLine()) {
                data[i] = in.nextLine();
                i++;
            }
            in.close();
        }catch (FileNotFoundException e) {
            System.out.println("File wos not found");
        }
        int[] demand = new int[getN()];
        for (int j = 1; j < data.length; j++) {
            String[] temp = data[j].split(" ");
            demand[j-1] = Integer.parseInt(temp[2]);
        }
        return demand;
    }

}
