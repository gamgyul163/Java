package PreMission;

// 백엔드 19기 임국희
public class JavaStudy01 {
    public static void main(String[] args) {
        System.out.println("[구구단 출력]");
        for (int i = 1; i <= 9; i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 1; j <= 9; j++) {
                line.append(String.format("%02d X %02d = %02d   ", j, i, j*i));
            }
            System.out.println(line.toString().trim());
        }
    }
}
