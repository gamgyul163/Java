// 백엔드 19기 임국희

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import static java.time.format.TextStyle.SHORT;
import static java.util.Locale.KOREAN;

public class JavaStudy05 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CalendarMaker calendarMaker = new CalendarMaker(scanner);

        System.out.println("[달력 출력 프로그램]");
        calendarMaker.askForYear();
        calendarMaker.askForMonth();

        calendarMaker.printCalendar();
    }
}

class CalendarMaker {
    private Scanner scanner = null;
    int year, month;

    CalendarMaker(Scanner scanner) {
        this.scanner = scanner;
    }
    void askForYear() {
        year = askForNumericInput("달력의 년도를 입력해 주세요.(yyyy):",4);
    }
    void askForMonth() {
        month = askForNumericInput("달력의 월을 입력해 주세요.(mm):",2);
    }
    
    // 숫자형 입력값을 입력받는 질문을 생성하는데 사용하는 매소드, prompt가 화면에 출력되어 입력을 유도하고 length로 길이를 제한한다.
    private int askForNumericInput(String prompt, int length) {
        int numericInput = -1;
        while (numericInput < 0) {
            try {
                System.out.print(prompt);
                String originalInput = scanner.nextLine();
                if (originalInput.length() != length) {
                    throw new NumberFormatException();
                }
                numericInput = Integer.parseInt(originalInput);
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 다시 입력해주세요");
            }
        }
        return numericInput;
    }
    // 달력의 타이틀 출력
    private void printCalendarTitle(LocalDate[] calendars) {
        for (LocalDate calendar:calendars) {
            String title = String.format("[%04d년 %02d월]", calendar.getYear(), calendar.getMonth().getValue());
            System.out.printf("%-38s",title);
        }
        System.out.println();
    }
    
    // 달력의 헤더(월~일) 출력, locale 값에따라 언어가 바뀐다.
    private void printCalendarHeader(LocalDate[] calendars, Locale locale) {
        StringBuilder header = new StringBuilder();
        for (int i = 1; i <= 7; i++) {
            DayOfWeek dayOfWeek = DayOfWeek.of(i);
            header.append(String.format("%-3s",dayOfWeek.getDisplayName(SHORT,locale)));
        }
        for (int i = 0; i < calendars.length; i++) {
            System.out.printf("%-34s",header);
        }
        System.out.println();
    }
    void printCalendar() {
        LocalDate[] calendars = new LocalDate[3];
        calendars[1] = LocalDate.of(year, month, 1);
        calendars[0] = calendars[1].minusMonths(1);
        calendars[2] = calendars[1].plusMonths(1);
        
        printCalendar(calendars);
    }
    void printCalendar(LocalDate[] calendars) {
        // 넘겨받은 배열의 달력을 출력하는 매소드
        int loop = calendars.length;
        ArrayList<Boolean> finishChecker = new ArrayList<>();
        for (int i = 0; i < loop; i++) {
            finishChecker.add(false);
        }


        // 달력 타이틀, 헤더 출력
        printCalendarTitle(calendars);
        printCalendarHeader(calendars, KOREAN);

        while (finishChecker.contains(false)) {
            for (int i = 0; i < loop; i++) {
                StringBuilder week = new StringBuilder();
                for (int k = 1; k <= 7; k++) {
                    DayOfWeek dayOfWeek = DayOfWeek.of(k);
                    int dayOfMonth = calendars[i].getDayOfMonth();
                    if (dayOfWeek.equals(calendars[i].getDayOfWeek()) && !finishChecker.get(i)) { // 요일이 맞고(초기부분 넘기기), 체커가 false인 경우
                        String strDay = String.format("%02d", dayOfMonth);
                        week.append(String.format("%-4s", strDay));
                        if (dayOfMonth == calendars[i].lengthOfMonth()) { // 달의 마지막날에 도달하면 체커를 true로 바꾼다.
                            finishChecker.set(i, true);
                        }
                        calendars[i] = calendars[i].plusDays(1);
                    } else {
                        week.append(String.format("%-4s", "")); // 초반에 요일 안맞는경우, 마지막까지 출력해서 체커가 true인 경우 공백을 추가한다.
                    }
                }
                System.out.printf("%-40s", week);
            }
            System.out.println();
        }
    }
}
