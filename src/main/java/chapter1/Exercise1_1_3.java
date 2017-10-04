package chapter1;

public class Exercise1_1_3 {

    public static void main(String... args) {
        check(1,2,3);
        check(2,2,2);
    }

    private static void check(int a, int b, int c) {
        if (a == b && b == c) {
            System.out.println("equal");
            return;
        }

        System.out.println("not equal");
    }

}
