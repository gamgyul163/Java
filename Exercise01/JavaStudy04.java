// 백엔드 19기 임국희

import java.util.Random;
import java.util.Scanner;

public class JavaStudy04 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ResidentRegistrationNumberMaker numberMaker = new ResidentRegistrationNumberMaker(scanner);

        System.out.println("[주민등록번호 계산]");

        numberMaker.askBirthYear();
        numberMaker.askBirthMonth();
        numberMaker.askBirthDay();
        numberMaker.askForGender();

        System.out.println(numberMaker.makeResidentRegistrationNumber());
        scanner.close();
    }
}
class ResidentRegistrationNumberMaker {
    private Scanner scanner;
    private int birthYear, birthMonth, birthDay;
    boolean gender; // true = male, false = female

    ResidentRegistrationNumberMaker(Scanner scanner) {
        this.scanner = scanner;
    }

    private int askForNumericInput(String prompt, int length) {
        int numericInput = -1;
        while (numericInput < 0) {
            try {
                System.out.print(prompt);
                String originalInput = scanner.nextLine();
                if(originalInput.length() != length) {
                    throw new NumberFormatException();
                }
                numericInput = Integer.parseInt(originalInput);
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 다시 입력해주세요");
            }
        }
        return numericInput;
    }
    void askBirthYear() {
        birthYear = askForNumericInput("출생년도를 입력해 주세요.(yyyy):",4);
    }
    void askBirthMonth() {
        birthMonth = askForNumericInput("출생월을 입력해 주세요.(mm):",2);
    }
    void askBirthDay() {
        birthDay = askForNumericInput("출생일을 입력해 주세요.(dd):",2);
    }
    void askForGender() {
        while (true) {
            System.out.print("성별을 입력해 주세요.(m/f):");
            String originalInput = scanner.nextLine();
            if (originalInput.equals("m")) {
                gender = true;
                break;
            } else if (originalInput.equals("f")) {
                gender = false;
                break;
            } else {
                System.out.println("잘못된 입력입니다. 'm' 또는 'f'를 입력해주세요");
            }
        }
    }
    int getGenderIdentifyNumber(int birthYear, boolean gender) {
        int genderIdentifyNumber = 0;
        switch (birthYear/100) {
            case 18: {
                genderIdentifyNumber = 9;
                break;
            }
            case 19: {
                genderIdentifyNumber = 1;
                break;
            }
            case 20: {
                genderIdentifyNumber = 3;
                break;
            }
        }
        if (gender) {
            return genderIdentifyNumber;
        } else {
            return genderIdentifyNumber + 1;
        }
    }
    String makeResidentRegistrationNumber() {
        StringBuilder newNumber = new StringBuilder();
        newNumber.append(birthYear%100);
        newNumber.append(birthMonth);
        newNumber.append(birthDay);
        newNumber.append("-");
        newNumber.append(getGenderIdentifyNumber(birthYear,gender));
        
        // 난수 생성
        Random random = new Random();
        int randomInt = random.nextInt(999999) + 1;
        newNumber.append(String.format("%06d",randomInt));
        
        return newNumber.toString();
    }
}
