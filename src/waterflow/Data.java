package waterflow;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Data {

    //INPUT
    private double[][] elevasi = null;
    private int rows = 0;//banyaknya baris data elevasi
    private int cols = 0;//banyaknya kolom data elevasi
    private ArrayList<Titik> listTitikPusat = new ArrayList<>();

    //OUTPUT
    private int[][] result = null;
    private ArrayList<Edge> graph = null;

    //Pewarnaan grid
    private double MIN = 0;
    private double MAX = 0;
    private Color[] colors = null;

    public Data(File fileData) {
        readDataElevationFromFile(fileData);
    }

    private void readDataElevationFromFile(File fileData) {
        double[][] dataElevation = null;
        try {
            FileInputStream fis = new FileInputStream(fileData);
            Scanner sc = new Scanner(fis, "UTF-8");

            // baca nCols
            int nCols = 0;
            if (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] splitLine = line.split("\\s+");//menghapus spasi
                String value = splitLine[1];//membaca data pada kolom-1 di array splitLine
                nCols = Integer.parseInt(value);
            }

            //baca nRows
            int nRows = 0;
            if (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] splitLine = line.split("\\s+");//menghapus spasi
                String value = splitLine[1];//membaca data pada kolom-1 di array splitLine
                nRows = Integer.parseInt(value);
            }

            //baca data
            if (nRows > 0 && nCols > 0) {
                double[][] newData = new double[nRows][];
                sc.nextLine();
                sc.nextLine();
                sc.nextLine();
                sc.nextLine();

                ///hitung MIN MAX
                double min = Double.MAX_VALUE;
                double max = Double.MIN_VALUE;

                //membaca data baris demi baris
                for (int i = 0; i < nRows; i++) {
                    if (sc.hasNextLine()) {
                        String line = sc.nextLine();
                        String[] splitLine = line.split("\\s+");
                        newData[i] = new double[splitLine.length];
                        for (int j = 0; j < newData[i].length; j++) {
                            String value = splitLine[j];
                            double dValue = 0;
                            try {
                                dValue = Double.parseDouble(value);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                            newData[i][j] = dValue;
                        }
                    }
                }//end of for (int i = 0; i < nRows; i++) 

                //set data elevasi
                if (newData != null) {
                    setElevasi(newData);
                }
            }
            sc.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//end of setData()  

    private void setElevationColor() {
        Color c1 = Color.decode("#c0392b");
        Color c2 = Color.decode("#e74c3c");
        Color c3 = Color.decode("#d35400");
        Color c4 = Color.decode("#e67e22");
        Color c5 = Color.decode("#f39c12");
        Color c6 = Color.decode("#f1c40f");
        Color c7 = Color.decode("#27ae60");
        Color c8 = Color.decode("#2ecc71");
        Color c9 = Color.decode("#ecf0f1");
        Color[] arrColors = {c9, c8, c7, c6, c5, c4, c3, c2, c1};
        this.colors = arrColors;
    }//end of setElevationColor()

    public void resetListTitikPusat() {
        listTitikPusat = new ArrayList<>();
    }//end of resetListTitikPusat()

    public void insertTitikPusat(Titik titik) {
        //validasi data elevasi
        if (this.elevasi != null
                && this.rows > 0
                && this.cols > 0
                && !listTitikPusat.contains(titik)
                && titik.getX() >= 0
                && titik.getX() < rows
                && titik.getY() >= 0
                && titik.getY() < cols) {
            listTitikPusat.add(titik);
        }
    }//end of insertTitikPusat(Titik titik)

    public void insertTitikPusat(Titik[] titik) {
        for (Titik t : titik) {
            insertTitikPusat(t);
        }
    }//end of insertTitikPusat(Titik titik)

    public double[][] getElevasi() {
        return elevasi;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public ArrayList<Titik> getListTitikPusat() {
        return listTitikPusat;
    }

    public int[][] getResult() {
        return result;
    }

    public ArrayList<Edge> getGraph() {
        return graph;
    }

    public double getMIN() {
        return MIN;
    }

    public double getMAX() {
        return MAX;
    }

    public Color[] getColors() {
        return colors;
    }

    public void setElevasi(double[][] elevasi) {
        if (elevasi != null) {
            int nRows = elevasi.length;
            int nCols = elevasi[0].length;
            double[][] newElevasi = new double[nRows][nCols];
            int[][] newResult = new int[nRows][nCols];
            ///hitung MIN MAX
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;

            for (int i = 0; i < elevasi.length; i++) {
                for (int j = 0; j < elevasi[i].length; j++) {
                    double value = elevasi[i][j];
                    newElevasi[i][j] = value;
                    newResult[i][j] = -1;
                    //cek min max
                    if (value < min) {
                        min = value;
                    }
                    if (value > max) {
                        max = value;
                    }
                }
            }

            //set data elevasi
            this.elevasi = newElevasi;
            this.result = newResult;
            this.rows = nRows;
            this.cols = nCols;
            this.MIN = min;
            this.MAX = max;
            this.setElevationColor();
        }//end of for (int i = 0; i < nRows; i++) 
    }

    public void setResult(int[][] result) {
        this.result = result;
    }

    public void setGraph(ArrayList<Edge> graph) {
        this.graph = graph;
    }

    public String getStringDataElevasi() {
        if (elevasi != null) {
            StringBuffer sb = new StringBuffer();
            sb.append("------------------------------------------------------------------------------------------------\n");
            sb.append("DATA ELEVASI\n");
            for (int i = 0; i < elevasi.length; i++) {
                for (int j = 0; j < elevasi[i].length; j++) {
                    sb.append(elevasi[i][j] + "\t");
                }
                sb.append("\n");
            }
            sb.append("------------------------------------------------------------------------------------------------\n");
            return sb.toString();
        } else {
            return "data elevasi = NULL";
        }
    }

    public String getStringDataResult() {
        if (result != null) {
            StringBuffer sb = new StringBuffer();
            sb.append("------------------------------------------------------------------------------------------------\n");
            sb.append("DATA RESULT\n");
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[i].length; j++) {
                    sb.append(result[i][j] + "\t");
                }
                sb.append("\n");
            }
            sb.append("------------------------------------------------------------------------------------------------\n");
            return sb.toString();
        } else {
            return "data result = NULL";
        }
    }

    @Override
    public String toString() {
        return "FLOW DIRECTION";
    }

}//end of class Data
