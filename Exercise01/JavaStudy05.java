// 백엔드 19기 임국희

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Scanner;

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
    private void printCalendarTitle(LocalDate[] calendars) {
        for (LocalDate calendar:calendars) {
            String title = String.format("[%04d년 %02d월]", calendar.getYear(), calendar.getMonth().getValue());
            System.out.printf("%-38s",title);
        }
        System.out.println();
    }

    private void printCalendarHeader() {
        StringBuilder header = new StringBuilder();
        for (int i = 1; i <= 7; i++) {
            DayOfWeek dayOfWeek = DayOfWeek.of(i);
            header.append(String.format("%-3s",dayOfWeek.getDisplayName(SHORT,KOREAN)));
        }
        for (int i = 0; i < 3; i++) {
            System.out.printf("%-34s",header);
        }
        System.out.println();
    }
    void printCalendar() {
        LocalDate[] calendars = new LocalDate[3];
        calendars[1] = LocalDate.of(year, month, 1);
        calendars[0] = calendars[1].minusMonths(1);
        calendars[2] = calendars[1].plusMonths(1);

        // 달력 타이틀, 헤더 출력
        printCalendarTitle(calendars);
        printCalendarHeader();

        boolean[] isFinish = new boolean[3];
        while (!isFinish[0]||!isFinish[1]||!isFinish[2]) {
            for (int j = 0; j < 3; j++) {
                StringBuilder week = new StringBuilder();
                for (int k = 1; k <= 7; k++) {
                    DayOfWeek dayOfWeek = DayOfWeek.of(k);
                    int dayOfMonth = calendars[j].getDayOfMonth();
                    if(dayOfWeek.equals(calendars[j].getDayOfWeek()) && !isFinish[j]) {
                        String strDay = String.format("%02d",dayOfMonth);
                        week.append(String.format("%-4s",strDay));
                        if (dayOfMonth == calendars[j].lengthOfMonth()) {
                            isFinish[j] = true;
                        }
                        calendars[j] = calendars[j].plusDays(1);
                    } else {
                        week.append(String.format("%-4s",""));
                    }
                }
                System.out.printf("%-40s",week);
            }
            System.out.println();
        }
    }
}
