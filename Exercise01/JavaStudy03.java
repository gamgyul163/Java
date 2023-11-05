// 백엔드 19기 임국희

import java.util.Scanner;

public class JavaStudy03 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntranceFeeCalculator calculator = new EntranceFeeCalculator(scanner);

        System.out.println("[입장권 계산]");

        calculator.askAge();
        calculator.askEntranceTime();
        calculator.askIsNationalMerit();
        calculator.askIsWelfareCard();

        System.out.printf("입장료: %d\n", calculator.getEntranceFee());
        scanner.close();
    }
}

class EntranceFeeCalculator {
    private final int NORMAL_FEE = 10000, DISCOUNTED_FEE = 8000, EXTRA_DISCOUNTED_FEE = 4000;
    private Scanner scanner = null;
    private int age, entranceTime;
    private boolean isNationalMerit, isWelfareCard;

    EntranceFeeCalculator(Scanner scanner) {
        this.scanner = scanner;
    }
    private int askForNumericInput(String prompt) {
        int numericInput = -1;
        while (numericInput < 0) {
            try {
                System.out.print(prompt);
                numericInput = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요");
            }
        }
        return numericInput;
    }
    void askAge() {
        age = askForNumericInput("나이를 입력해 주세요.(숫자):");
    }
    void askEntranceTime() {
        entranceTime = askForNumericInput("입장시간을 입력해 주세요.(숫자입력):");
    }
    private boolean askForBooleanInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String originalInput = scanner.nextLine();
            if (originalInput.equals("y")) {
                return true;
            } else if (originalInput.equals("n")) {
                return false;
            } else {
                System.out.println("잘못된 입력입니다. 'y' 또는 'n'을 입력해주세요");
            }
        }
    }
    void askIsNationalMerit() {
        isNationalMerit = askForBooleanInput("국가유공자 여부를 입력해 주세요(y/n):");
    }
    void askIsWelfareCard() {
        isWelfareCard = askForBooleanInput("복지카드 여부를 입력해 주세요(y/n)");
    }

    int getEntranceFee() {
        if (age < 3) {
            // 3세미만이면 무료
            return 0;
        } else if (age < 13 || entranceTime >= 17) {
            // 13미만이거나 17시 이후 입장이면 특별할인
            return EXTRA_DISCOUNTED_FEE;
        } else if (isNationalMerit || isWelfareCard) {
            // 국가유공자나 복지카드면 일반할인
            return DISCOUNTED_FEE;
        } else {
            return NORMAL_FEE;
        }
    }
}