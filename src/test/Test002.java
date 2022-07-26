package test;

import java.util.Arrays;
import java.util.Scanner;

public class Test002 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        int[][]data = new int[n][m];
        
//        for(int i=0;i<data.length;i++){
//            Arrays.fill(data[i], -1);
//        }
        
        for(int i=0;i<data.length;i++){
            for (int j = 0; j < data[i].length; j++) {
                data[i][j]=-1;
            }
        }
        //cetak
        
        for(int i=0;i<data.length;i++){
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j]+"\t");;
            }
            System.out.println("");
        }
    }
}
