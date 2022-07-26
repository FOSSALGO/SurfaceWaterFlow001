package test;

import java.util.Arrays;

public class Test004 {

    public static void main(String[] args) {
        //array childs--------------------------------------------------
        int[][] childs = new int[9][2];
        //D0------------------------
        childs[0] = new int[]{0};
        //D1------------------------
        childs[1] = new int[]{16, 9};
        //D2------------------------
        childs[2] = new int[]{9, 10};
        //D3------------------------
        childs[3] = new int[]{10, 11};
        //D4------------------------
        childs[4] = new int[]{11, 12};
        //D5------------------------
        childs[5] = new int[]{12, 13, 14, 15};
        //D6------------------------
        childs[6] = new int[]{13, 14};
        //D7------------------------
        childs[7] = new int[]{14, 15};
        //D8------------------------
        childs[8] = new int[]{15, 16};
        //end of inisialisasi array childs------------------------------

        int arah = 8;
        //System.out.println(childs[arah][0]+", "+childs[arah][1]);
//        for (int j = 0; j < childs[arah].length; j++) {
//            if (j > 0) {
//                System.out.print(", ");
//            }
//            System.out.print(childs[arah][j]);
//        }
//        System.out.println(" ");

        //System.out.println(Arrays.toString(childs[arah]));
        int[] budi = childs[arah];
        int[] child = childs[arah];
//          System.out.println(Arrays.toString(budi));

        for (int i = 0; i < budi.length; i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(budi[i]);
        }
        System.out.println("");
    }
}
