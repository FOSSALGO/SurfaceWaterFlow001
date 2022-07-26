package waterflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class AlgoritmaD8 implements Algorithm {

    private Data data = null;

    public AlgoritmaD8(Data data) {
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
                    result[titik.getX()][titik.getY()] = 1;
                    antrianTitik.add(titik);
                }
            }

            //melakukan loop pencarian D8
            while (!antrianTitik.isEmpty()) {
                Titik center = antrianTitik.poll();//baca titik center

                //origin
                int io = center.getX();
                int jo = center.getY();

                //destination
                int id = -1;
                int jd = -1;

                //ALGORITMA D8
                //SKEMA D8
                // + -- +  -- +  -- + 
                // | D8 |  D1 |  D2 |
                // + -- +  -- +  -- + 
                // | D7 |  D0 |  D3 |
                // + -- +  -- +  -- + 
                // | D6 |  D5 |  D4 |
                // + -- +  -- +  -- + 
                //array neighbous-----------------------------------------------
                int[][] neighbors = new int[9][2];
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
                //end of inisialiasi array neighbous----------------------------
                
                int arah = 0;
                double MIN = Double.MAX_VALUE;//inisialisasi titik terndah dengan nilai MAX
                for (int d = 1; d <= 8; d++) {
                    int I = neighbors[d][0];
                    int J = neighbors[d][1];
                    if (I >= 0 && I < rows && J >= 0 && J < cols && dataElevasi[I][J] < MIN) {
                        MIN = dataElevasi[I][J];
                        arah = d;
                        id = I;
                        jd = J;
                    }
                }

                //SET ARAH ALIRAN
                if (MIN < dataElevasi[io][jo] && arah > 0) {
                    result[io][jo] = arah;
                    Titik origin = center;
                    Titik destination = new Titik(id, jd);
                    Edge edge = new Edge(origin, destination);
                    graph.add(edge);//menambahkan edge baru ke graph
                    if (result[destination.getX()][destination.getY()] == -1 && !antrianTitik.contains(destination)) {
                        //result[destination.getX()][destination.getY()] = 0;//tandai result
                        antrianTitik.offer(destination);//tambahkan titik destination ke dalam antrian titik
                    }
                } else {
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
