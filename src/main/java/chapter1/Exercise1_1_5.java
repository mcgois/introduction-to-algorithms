package chapter1;

public class Exercise1_1_5 {

    public static void main(String... args) {
        System.out.println(check(0.99999));
        System.out.println(check(1.0));
    }

    private static boolean check(double value) {
        if (value < 1 && value > 0) {
            return true;
        }

        return false;
    }

}
