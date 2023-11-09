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
        LocalDate localDate = calendarMaker.askLocalDate();
        calendarMaker.printCalendar(localDate, 1, 1);
    }
}

class CalendarMaker {
    private final Scanner scanner;
    private final String INPUT_ERROR_MESSAGE = "잘못된 입력입니다. 다시 입력해주세요";

    CalendarMaker(Scanner scanner) {
        this.scanner = scanner;
    }

    private int askYear() {
        return askNumericInput("달력의 년도를 입력해 주세요.(yyyy):",4);
    }
    private int askMonth() {
        return askNumericInput("달력의 월을 입력해 주세요.(mm):",2);
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
    LocalDate askLocalDate() {
        return LocalDate.of(askYear(), askMonth(), 1);
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
            header.append(String.format("%s\t",dayOfWeek.getDisplayName(SHORT,locale)));
        }
        for (int i = 0; i < calendars.length; i++) {
            System.out.printf("%-26s", header);
        }
        System.out.println();
    }
    void printCalendar(LocalDate localDate, int prevCalendar, int nextCalendar) {
        LocalDate[] calendars = new LocalDate[prevCalendar+nextCalendar+1];
        for (int i = 0; i < calendars.length; i++) {
            calendars[i] = localDate.plusMonths(i-prevCalendar);
        }
        
        printCalendar(calendars);
    }
    void printCalendar(LocalDate[] calendars) {
        // 넘겨받은 배열의 달력을 출력하는 매소드
        int loop = calendars.length;
        ArrayList<Boolean> finishChecker = new ArrayList<>(); // 달력 출력 완료여부 체커
        for (int i = 0; i < loop; i++) {
            finishChecker.add(false);
        }
        
        // 달력 타이틀, 헤더 출력
        printCalendarTitle(calendars);
        printCalendarHeader(calendars, KOREAN);

        while (finishChecker.contains(false)) { // 체커가 false인게 있으면 계속 돌린다.
            for (int i = 0; i < loop; i++) { // 넘겨받은 달력dmf 하나씩 돌린다.
                StringBuilder week = new StringBuilder(); // 주단위로 저장해서 한번에 출력한다.
                for (int j = 1; j <= 7; j++) {
                    DayOfWeek dayOfWeek = DayOfWeek.of(j);
                    int dayOfMonth = calendars[i].getDayOfMonth();
                    if (dayOfWeek.equals(calendars[i].getDayOfWeek()) && !finishChecker.get(i)) { // 요일이 맞고(초기부분 넘기기), 체커가 false인 경우
                        week.append(String.format("%02d\t", dayOfMonth));
                        if (dayOfMonth == calendars[i].lengthOfMonth()) { // 달의 마지막날에 도달하면 체커를 true로 바꾼다.
                            finishChecker.set(i, true);
                        }
                        calendars[i] = calendars[i].plusDays(1); // 다음날로 날짜를 바꾼다.
                    } else {
                        week.append("  \t"); // 초반에 요일 안맞는경우, 마지막까지 출력해서 체커가 true인 경우 공백을 추가한다.
                    }
                }
                System.out.printf("%-33s",week);
            }
            System.out.println();
        }
    }
}
