package PreMission;// 백엔드 19기 임국희

import java.util.Scanner;

public class JavaStudy02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CashBackCalculator calculator = new CashBackCalculator(scanner);

        System.out.println("[캐시백 계산]");

        calculator.printCashBackAmount(calculator.askPaymentAmount());
    }
}

class CashBackCalculator { // 캐시백 계산기
    private final Scanner scanner;
    private final int CASH_BACK_RATIO = 10; // 캐시백 비율
    private final String INPUT_ERROR_MESSAGE = "잘못된 입력입니다. 다시 입력해주세요";
    CashBackCalculator(Scanner scanner) {
        this.scanner = scanner;
    }

    int askPaymentAmount() { // 입력 유도 및 유효성 검증
        int numericInput;
        do {
            System.out.print("결제 금액을 입력해 주세요.(금액):");
            String strInput = scanner.nextLine().trim();
            if(strInput.matches("\\d+")) {
                numericInput = Integer.parseInt(strInput);
                break;
            }
            System.out.println(INPUT_ERROR_MESSAGE);
        } while (true);
        return numericInput;
    }
    private int calCashBackAmount(int paymentAmount) { // 캐시백 계산
        int cashBackAmount = paymentAmount*CASH_BACK_RATIO/100;
        cashBackAmount -= cashBackAmount%100; // 100이하 절삭
        if (cashBackAmount > 300) { // 캐시백은 최대 300
            cashBackAmount = 300;
        }
        return cashBackAmount;
    }
    void printCashBackAmount(int paymentAmount) { // 캐시백 계산 결과 출력
        System.out.printf("결제 금액은 %d원이고, 캐시백은 %d원 입니다.",paymentAmount, calCashBackAmount(paymentAmount));
    }
}