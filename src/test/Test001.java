package test;

import java.io.File;
import waterflow.Data;

public class Test001 {
    public static void main(String[] args) {        
        File f = new File("0-dem.txt");
        Data data = new Data(f);
        System.out.println(data.getStringDataElevasi());
        System.out.println(data.getStringDataResult());
    }
}
