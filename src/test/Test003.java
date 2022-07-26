package test;

public class Test003 {

    public static void main(String[] args) {
        int size = 10;
        double MAX = 27;
        double MIN = -3;
        double input = -3;
        int index = getIndexColor(size, MAX, MIN, input);
        System.out.println(index);

    }

    public static int getIndexColor(int size, double MAX, double MIN, double value) {
        if (value <= MIN) {
            return 0;
        } else if (value >= MAX) {
            return size - 1;
        } else {
            double satuan = (MAX - MIN) / size;
            double div = value / satuan;
            int index = (int) Math.ceil(div);
        return index;
        }
    }
}
