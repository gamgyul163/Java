import java.util.Scanner;

public class AmusementPark {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int age, entranceTime, entranceFee = 10000;
        boolean isNationalMerit, isWelfareCard;

        System.out.println("[입장권 계산]");
        System.out.print("나이를 입력해 주세요.(숫자):");
        age = sc.nextInt();
        sc.nextLine();
        System.out.print("입장시간을 입력해 주세요.(숫자입력):");
        entranceTime = sc.nextInt();
        sc.nextLine();
        System.out.print("국가유공자 여부를 입력해 주세요.(y/n):");
        isNationalMerit = sc.nextLine().equals("y")? true:false;
        System.out.print("복지카드 여부를 입력해 주세요.(y/n):");
        isWelfareCard = sc.nextLine().equals("y")? true:false;

        if (age <3) {
            entranceFee = 0;
        } else if (age <13 || entranceTime >= 17) {
            entranceFee = 4000;
        } else if (isNationalMerit || isWelfareCard) {
            entranceFee = 8000;
        }

        System.out.printf("입장료: %d",entranceFee);
    }
}
