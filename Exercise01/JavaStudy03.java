// 백엔드 19기 임국희

import java.util.Scanner;

public class JavaStudy03 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntranceFeeCalculator calculator = new EntranceFeeCalculator(scanner);

        System.out.println("[입장권 계산]");
        Visitor visitor = calculator.askVisitorInfo();
        calculator.printCalEntranceFee(visitor);
    }
}
class Visitor { // 입장객
    private final int age, entranceTime;
    private final boolean isNationalMerit, isWelfareCard;

    Visitor(int age, int entranceTime, boolean isNationalMerit, boolean isWelfareCard) {
        this.age = age;
        this.entranceTime = entranceTime;
        this.isNationalMerit = isNationalMerit;
        this.isWelfareCard = isWelfareCard;
    }

    int getAge() {
        return age;
    }
    int getEntranceTime() {
        return entranceTime;
    }
    boolean getIsNationalMerit() {
        return isNationalMerit;
    }
    boolean getIsWelfareCard() {
        return isWelfareCard;
    }
}
class EntranceFeeCalculator {
    private final int NORMAL_FEE = 10000, DISCOUNTED_FEE = 8000, EXTRA_DISCOUNTED_FEE = 4000; // 요금
    private final Scanner scanner;
    private final String INPUT_ERROR_MESSAGE = "잘못된 입력입니다. 다시 입력해주세요";
    private final int FREE_AGE = 3; // 무료 입장
    private final int SALE_AGE = 13, SALE_TIME = 17; // 세일 기준 나이, 기준 시간

    EntranceFeeCalculator(Scanner scanner) {
        this.scanner = scanner;
    }
    private int askNumericInput(String prompt) { // 숫자 입력 유도 및 유효성 검증
        int numericInput;
        do {
            System.out.print(prompt);
            String strInput = scanner.nextLine().trim();
            if(strInput.matches("\\d+")) {
                numericInput = Integer.parseInt(strInput);
                break;
            }
            System.out.println(INPUT_ERROR_MESSAGE);
        } while (true);
        return numericInput;
    }
    private int askAge() {
        return askNumericInput("나이를 입력해 주세요.(숫자):");
    }
    private int askEntranceTime() {
        return askNumericInput("입장시간을 입력해 주세요.(숫자입력):");
    }
    private boolean askBoolInput(String prompt) { // y, n 입력 유도 및 유효성 검증
        boolean boolInput;
        do {
            System.out.print(prompt);
            String strInput = scanner.nextLine().trim();
            if(strInput.matches("[yn]")) {
                boolInput = (strInput.equals("y"));
                break;
            }
            System.out.println(INPUT_ERROR_MESSAGE);
        } while (true);
        return boolInput;
    }
    private boolean askIsNationalMerit() {
        return askBoolInput("국가유공자 여부를 입력해 주세요(y/n):");
    }
    private boolean askIsWelfareCard() {
        return askBoolInput("복지카드 여부를 입력해 주세요(y/n)");
    }
    private int calEntranceFee(Visitor visitor) { // 입장객 정보를 받아서 입장료를 계산
        if (visitor.getAge() < FREE_AGE) {
            // 기준 나이 미만이면 무료
            return 0;
        } else if (visitor.getAge() < SALE_AGE || visitor.getEntranceTime() >= SALE_TIME) {
            // 시간, 나이에 따라 할인 적용
            return EXTRA_DISCOUNTED_FEE;
        } else if (visitor.getIsNationalMerit() || visitor.getIsWelfareCard()) {
            // 국가유공자나 복지카드면 일반할인
            return DISCOUNTED_FEE;
        } else {
            return NORMAL_FEE;
        }
    }
    void printCalEntranceFee(Visitor visitor) { // 입장객 정보를 받아서 입자료를 출력
        if (visitor == null) {
            System.out.println("입장객 정보가 없습니다.");
        } else {
            System.out.printf("입장료:%10d", calEntranceFee(visitor));
        }
    }
    Visitor askVisitorInfo() { // 입장객 정보를 입력해서 한번에 돌려준다.
        return new Visitor(askAge(), askEntranceTime(), askIsNationalMerit(), askIsWelfareCard());
    }
}