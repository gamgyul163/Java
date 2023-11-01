public class TimesTable {
    public static void main(String[] args) {
        System.out.println("[구구단 출력]");
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                System.out.print(String.format("%02d X %02d = %02d", j, i, j*i));
                if (j < 9) {
                    System.out.print(" ");
                } else {
                    System.out.print("\n");
                }
            }
        }
    }
}
