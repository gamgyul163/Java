import java.util.Random;
import java.util.Scanner;

public class ResidentRegistrationNumber {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder residentRegistrationNumber = new StringBuilder();

        System.out.println("[주민등록번호 계산]");
        System.out.print("출생년도를 입력해 주세요.(yyyy):");
        residentRegistrationNumber.append(sc.nextLine().substring(2));
        System.out.print("출생월을 입력해 주세요.(mm):");
        residentRegistrationNumber.append(sc.nextLine());
        System.out.print("출생일을 입력해 주세요.(dd):");
        residentRegistrationNumber.append(sc.nextLine());

        residentRegistrationNumber.append("-");

        System.out.print("성별을 입력해 주세요.(m/f):");
        residentRegistrationNumber.append(sc.nextLine().equals("m")? "3":"4");

        Random random = new Random();
        while (residentRegistrationNumber.length() < 14) {
            String nextNum = Integer.toString(random.nextInt(1,1000000));
            if(residentRegistrationNumber.length()+nextNum.length() <= 14) {
                residentRegistrationNumber.append(nextNum);
            }
        }
        System.out.println(residentRegistrationNumber);
    }
}
