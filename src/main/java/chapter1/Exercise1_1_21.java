package chapter1;

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Exercise1_1_21 {

    static class Data {
        private String name;
        private Integer number1;
        private Integer number2;

        public Data(String name, Integer number1, Integer number2) {
            this.name = name;
            this.number1 = number1;
            this.number2 = number2;
        }

    }

    public static void main(String... args) {
        List<Data> dataList = new ArrayList<Data>();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String name = scanner.next();
            Integer number1 = scanner.nextInt();
            Integer number2 = scanner.nextInt();
            dataList.add(new Data(name, number1, number2));
        }

        for (Data data : dataList) {
            double div = ((double) data.number1) / data.number2;
            StdOut.printf("%s %d %d %.3f\n", data.name, data.number1, data.number2, div);
        }
    }

}
