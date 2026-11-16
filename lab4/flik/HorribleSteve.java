package flik;

public class HorribleSteve {
    private static final int N = 500;

    public static void main(String[] args) throws Exception {
        int i = 0;
        for (int j = 0; i < N; ++i, ++j) {
            if (!Flik.isSameNumber(i, j)) {
                throw new Exception(
                        String.format("i:%d not same as j:%d ??", i, j));
            }
        }
        System.out.println("i is " + i);
    }
}
