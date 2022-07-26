package waterflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class AlgoritmaD24 implements Algorithm {

    private Data data = null;

    public AlgoritmaD24(Data data) {
        this.data = data;
        this.flowDirection();
    }

    public void flowDirection() {
        int[][] result = null;
        ArrayList<Edge> graph = null;

        double[][] dataElevasi = data.getElevasi();
        int rows = data.getRows();
        int cols = data.getCols();
        ArrayList<Titik> listTitikPusat = data.getListTitikPusat();
        if (dataElevasi != null && listTitikPusat != null && !listTitikPusat.isEmpty()) {
            result = new int[rows][cols];
            //set nilai deafault untuk array result = -1
            for (int i = 0; i < result.length; i++) {
                Arrays.fill(result[i], -1);
            }

            //inisialisasi graph
            graph = new ArrayList<>();

            //membuat antrian titik untuk dievaluasi arah alirannya
            Queue<Titik> antrianTitik = new LinkedList<>();

            //masukkan semua titik pusat yang ada di list ke dalam antrian
            for (Titik titik : listTitikPusat) {
                if (titik.getX() >= 0 && titik.getX() < rows && titik.getY() >= 0 && titik.getY() < cols && !antrianTitik.contains(titik)) {
                    ////////////result[titik.getX()][titik.getY()] = 1;
                    antrianTitik.add(titik);
                }
            }

            //melakukan loop pencarian D8
            while (!antrianTitik.isEmpty()) {
                Titik center = antrianTitik.poll();//baca titik center

                //origin
                int io = center.getX();
                int jo = center.getY();

                //destination_R1
                int id = -1;
                int jd = -1;

                //ALGORITMA D24
                //skema D24-----------------------------------------------------
                // + --- + --- + --- + --- + --- +
                // | D23 | D24 | D09 | D10 | D11 |
                // + --- + --- + --- + --- + --- +
                // | D22 | D08 | D01 | D02 | D12 |
                // + --- + --- + --- + --- + --- +
                // | D21 | D07 | D00 | D03 | D13 |
                // + --- + --- + --- + --- + --- +
                // | D20 | D06 | D05 | D04 | D14 |
                // + --- + --- + --- + --- + --- +
                // | D19 | D18 | D17 | D16 | D15 |
                // + --- + --- + --- + --- + --- +
                //array neighbous-----------------------------------------------
                int[][] neighbors = new int[25][2];
                //D0------------------------
                neighbors[0][0] = io;
                neighbors[0][1] = jo;
                //D1------------------------
                neighbors[1][0] = io - 1;
                neighbors[1][1] = jo;
                //D2------------------------
                neighbors[2][0] = io - 1;
                neighbors[2][1] = jo + 1;
                //D3------------------------
                neighbors[3][0] = io;
                neighbors[3][1] = jo + 1;
                //D4------------------------
                neighbors[4][0] = io + 1;
                neighbors[4][1] = jo + 1;
                //D5------------------------
                neighbors[5][0] = io + 1;
                neighbors[5][1] = jo;
                //D6------------------------
                neighbors[6][0] = io + 1;
                neighbors[6][1] = jo - 1;
                //D7------------------------
                neighbors[7][0] = io;
                neighbors[7][1] = jo - 1;
                //D8------------------------
                neighbors[8][0] = io - 1;
                neighbors[8][1] = jo - 1;
                //D9------------------------
                neighbors[9][0] = io - 2;
                neighbors[9][1] = jo;
                //D10------------------------
                neighbors[10][0] = io-2;
                neighbors[10][1] = jo+1;
                //D11------------------------
                neighbors[11][0] = io-2;
                neighbors[11][1] = jo+2;
                //D12------------------------
                neighbors[12][0] = io-1;
                neighbors[12][1] = jo+2;
                //D13------------------------
                neighbors[13][0] = io;
                neighbors[13][1] = jo+2;
                //D14------------------------
                neighbors[14][0] = io+1;
                neighbors[14][1] = jo+2;
                //D15------------------------
                neighbors[15][0] = io+2;
                neighbors[15][1] = jo+2;
                //D16------------------------
                neighbors[16][0] = io+2;
                neighbors[16][1] = jo+1;
                //D17------------------------
                neighbors[17][0] = io+2;
                neighbors[17][1] = jo;
                //D18------------------------
                neighbors[18][0] = io+2;
                neighbors[18][1] = jo-1;
                //D19------------------------
                neighbors[19][0] = io+2;
                neighbors[19][1] = jo-2;
                //D20------------------------
                neighbors[20][0] = io+1;
                neighbors[20][1] = jo-2;
                //D21------------------------
                neighbors[21][0] = io;
                neighbors[21][1] = jo-2;
                //D22------------------------
                neighbors[22][0] = io-1;
                neighbors[22][1] = jo-2;
                //D23------------------------
                neighbors[23][0] = io-2;
                neighbors[23][1] = jo-2;
                //D24------------------------
                neighbors[24][0] = io-2;
                neighbors[24][1] = jo-1;
                //end of inisialiasi array neighbous----------------------------

                //array children------------------------------------------------
                int[][] children = new int[9][2];
                //D0------------------------
                children[0] = new int[]{1,2,3,4,5,6,7,8};//1, 2, 3, 4, 5, 6, 7, 8};
                //D1------------------------
                children[1] = new int[]{24,9,10};
                //D2------------------------
                children[2] = new int[]{10,11,12};
                //D3------------------------
                children[3] = new int[]{12,13,14};
                //D4------------------------
                children[4] = new int[]{14,15,16};
                //D5------------------------
                children[5] = new int[]{16,17,18};
                //D6------------------------
                children[6] = new int[]{18,19,20};
                //D7------------------------
                children[7] = new int[]{20,21,22};
                //D8------------------------
                children[8] = new int[]{22,23,24};
                //end of inisialisasi array children----------------------------

                int arah = 0;
                double MIN = Double.MAX_VALUE;//inisialisasi titik terndah dengan nilai MAX
                for (int d = 1; d <= 8; d++) {
                    int I = neighbors[d][0];
                    int J = neighbors[d][1];
                    if (I >= 0 && I < rows && J >= 0 && J < cols && dataElevasi[I][J] < MIN&& result[I][J]==-1) {
                        MIN = dataElevasi[I][J];
                        arah = d;
                        id = I;
                        jd = J;
                    }
                }

                //SET ARAH ALIRAN
                if (MIN < dataElevasi[io][jo] && arah > 0) {
                    result[io][jo] = arah;
                    int[] children_1 = children[arah];
                    int arah_1 = arah;
                    //destination_R2
                    int id_1 = -1;
                    int jd_1 = -1;
                    MIN = Double.MAX_VALUE;//inisialisasi titik terndah dengan nilai MAX
                    for (int c = 0; c < children_1.length; c++) {
                        int d = children_1[c];
                        int I = neighbors[d][0];
                        int J = neighbors[d][1];
                        if (I >= 0 && I < rows && J >= 0 && J < cols && dataElevasi[I][J] < MIN && result[I][J]==-1) {
                            MIN = dataElevasi[I][J];
                            arah_1 = d;
                            id_1 = I;
                            jd_1 = J;
                        }
                    }//end of for

                    if (MIN < dataElevasi[id][jd] && arah > 0) {//set RING_2
                        result[io][jo] = arah;
                        result[id][jd] = arah_1;
                        Titik origin = center;
                        Titik destination = new Titik(id_1, jd_1);
                        Edge edge = new Edge(origin, destination);
                        graph.add(edge);//menambahkan edge baru ke graph
                        if (result[destination.getX()][destination.getY()] == -1 && !antrianTitik.contains(destination)) {
                            //result[destination.getX()][destination.getY()] = 0;//tandai result dengan arah default
                            antrianTitik.offer(destination);//tambahkan titik destination ke dalam antrian titik
                        }
                    } else {//set RING_1
                        result[io][jo] = arah;
                        Titik origin = center;
                        Titik destination = new Titik(id, jd);
                        Edge edge = new Edge(origin, destination);
                        graph.add(edge);//menambahkan edge baru ke graph
                        if (result[destination.getX()][destination.getY()] == -1 && !antrianTitik.contains(destination)) {
                            //result[destination.getX()][destination.getY()] = 0;//tandai result dengan arah default
                            antrianTitik.offer(destination);//tambahkan titik destination ke dalam antrian titik
                        }
                    }
                } else {//set Center
                    result[io][jo] = 0;
                }

            }//end of while

            //set result
            data.setResult(result);
            data.setGraph(graph);

        }//end of if dataElevasi != null
    }//end of flowDirection()    

    public Data getData() {
        return this.data;
    }

}//end of class
