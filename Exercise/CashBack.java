import java.util.Scanner;

public class CashBack {
    public static void main(String[] args) {
        System.out.println("[캐시백 계산]");
        System.out.print("결제 금액을 입력해 주세요.(금액):");
        Scanner sc = new Scanner(System.in);
        int paymentAmount = sc.nextInt();
        int cashBackRatio = 10;
        int cashBackAmount = paymentAmount*cashBackRatio/100;
        cashBackAmount -= cashBackAmount%100; // 100이하 절삭
        if (cashBackAmount > 300) {
            cashBackAmount = 300;
        }
        System.out.printf("결제 금액은 %d원이고, 캐시백은 %d원 입니다.", paymentAmount, cashBackAmount);
    }
}
