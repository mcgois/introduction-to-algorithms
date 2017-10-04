package chapter1;

public class Exercise1_1_18 {

    public static void main(String... args) {
        System.out.println(mystery(2, 4));
        System.out.println(mystery(3, 11));
        System.out.println(mystery(4, 11));

    }

    private static int mystery(int a, int b) {
        if (b == 0) {
            return 1;
        }
        if (b % 2 == 0) {
            return mystery(a * a, b / 2);
        }
        return mystery(a * a, b / 2) * a;
    }

}
