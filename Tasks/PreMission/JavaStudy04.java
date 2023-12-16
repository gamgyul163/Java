package PreMission;// 백엔드 19기 임국희

import java.util.Random;
import java.util.Scanner;

public class JavaStudy04 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ResidentRegistrationNumberMaker numberMaker = new ResidentRegistrationNumberMaker(scanner);

        System.out.println("[주민등록번호 계산]");
        PersonalInfo personalInfo = numberMaker.askPersonalInfo();
        System.out.println(numberMaker.makeResidentRegistrationNumber(personalInfo));
    }
}
class PersonalInfo {
    private final int birthYear, birthMonth, birthDay;
    private final boolean isMale; // true = male, false = female
    private String registrationNumber;


    PersonalInfo(int birthYear,int birthMonth,int birthDay, boolean isMale) {
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.isMale = isMale;
    }

    int getBirthYear() {
        return birthYear;
    }
    int getBirthMonth() {
        return birthMonth;
    }
    int getBirthDay() {
        return birthDay;
    }
    boolean getisMale() {
        return isMale;
    }
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
class ResidentRegistrationNumberMaker {
    private final Scanner scanner;
    private final String INPUT_ERROR_MESSAGE = "잘못된 입력입니다. 다시 입력해주세요";

    ResidentRegistrationNumberMaker(Scanner scanner) {
        this.scanner = scanner;
    }

    private int askNumericInput(String prompt, int length) { // 숫자 입력 유도 및 유효성 검증
        int numericInput;
        do {
            System.out.print(prompt);
            String strInput = scanner.nextLine().trim();
            if(strInput.matches("\\d{"+length+"}")) {
                numericInput = Integer.parseInt(strInput);
                break;
            }
            System.out.println(INPUT_ERROR_MESSAGE);
        } while (true);
        return numericInput;
    }
    private int askBirthYear() {
        return askNumericInput("출생년도를 입력해 주세요.(yyyy):",4);
    }
    private int askBirthMonth() {
        return askNumericInput("출생월을 입력해 주세요.(mm):",2);
    }
    private int askBirthDay() {
        return askNumericInput("출생일을 입력해 주세요.(dd):",2);
    }
    private boolean askIsMale() {
        boolean boolInput;
        do {
            System.out.print("성별을 입력해 주세요.(m/f):");
            String strInput = scanner.nextLine().trim();
            if(strInput.matches("[mf]")) {
                boolInput = (strInput.equals("m"));
                break;
            }
            System.out.println(INPUT_ERROR_MESSAGE);
        } while (true);
        return boolInput;
    }
    private int getGenderIdentifyNumber(int birthYear, boolean isMale) {
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
        if (isMale) {
            return genderIdentifyNumber;
        } else {
            return genderIdentifyNumber + 1;
        }
    }
    PersonalInfo askPersonalInfo() {
        return new PersonalInfo(askBirthYear(), askBirthMonth(), askBirthDay(), askIsMale());
    }
    String makeResidentRegistrationNumber(PersonalInfo personalInfo) { // 개인정보를 받아서 주민 번호를 반환한다.
        StringBuilder newNumber = new StringBuilder();
        newNumber.append(personalInfo.getBirthYear()%100);
        newNumber.append(personalInfo.getBirthMonth());
        newNumber.append(personalInfo.getBirthDay());
        newNumber.append("-");
        newNumber.append(getGenderIdentifyNumber(personalInfo.getBirthYear(),personalInfo.getisMale()));
        
        // 난수 생성
        Random random = new Random();
        int randomInt = random.nextInt(999999) + 1;
        newNumber.append(String.format("%06d",randomInt));

        personalInfo.setRegistrationNumber(newNumber.toString());
        return newNumber.toString();
    }
}
