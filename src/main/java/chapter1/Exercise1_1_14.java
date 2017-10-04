package chapter1;

public class Exercise1_1_14 {

    public static void main(String... args) {
        System.out.println(lg(4));
        System.out.println(lg(3));
    }

    private static int lg(int N) {
        int i = 0;
        int power = 1;
        while (power <= N) {
            power *= 2;
            i++;
        }
        return i-1;
    }

}
