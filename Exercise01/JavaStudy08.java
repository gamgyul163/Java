// 백엔드 19기 임국희

import java.util.ArrayList;
import java.util.Scanner;

public class JavaStudy08 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaxCalculator taxCalculator = new TaxCalculator(scanner);
        long income = taxCalculator.askIncome();

        taxCalculator.printTaxByTaxRate(income);
        taxCalculator.printTaxByProgressiveDeductions(income);
    }
}
class TaxCalculator {
    private final Scanner scanner;
    private final long[] TAX_BASE = {0,12_000_000, 46_000_000, 88_000_000, 150_000_000, 300_000_000, 500_000_000, 1_000_000_000};
    private final long[] PROGRESSIVE_DEDUCTIONS = {0, 1_080_000, 5_220_000, 14_900_000, 19_400_000, 25_400_000, 35_400_000, 65_400_000};
    private final long[] TAX_RATE = {6, 15, 24, 35, 38, 40, 42, 45};
    private final String INPUT_ERROR_MESSAGE = "잘못된 입력입니다. 다시 입력해주세요";

    TaxCalculator(Scanner scanner) {
        this.scanner = scanner;
    }

    long askIncome() {
        long numericInput;
        do {
            System.out.print("연소득을 입력해 주세요.:");
            String strInput = scanner.nextLine().trim();
            if(strInput.matches("\\d+")) {
                numericInput = Long.parseLong(strInput);
                break;
            }
            System.out.println(INPUT_ERROR_MESSAGE);
        } while (true);
        return numericInput;
    }
    private ArrayList<long[]> calTaxByTaxRate(long income) { // 각 구간에 해당되는 금액을 어레이로 담아서 리턴
        ArrayList<long[]> tax = new ArrayList<>();
        for (int i = 1; i < TAX_RATE.length; i++) { // i : 현재 과세 표준, i-1:현재 세율
            long calNum = Math.min(income, TAX_BASE[i]) - TAX_BASE[i-1]; // 과세 구간을 지나며 소득과 현재 과세구간중 작은쪽에 바로 이전 단계 과세 표준을 뺀다.
            tax.add(new long[]{calNum, calNum*TAX_RATE[i-1]/100});
            if (income <= TAX_BASE[i]) { // 소득이 현재 과세 표준보다 작으면 종료
                break;
            } else if (i == TAX_RATE.length-1) { // 최고 과세 구간 처리
                tax.add(new long[]{income - TAX_BASE[i], (income - TAX_BASE[i])*TAX_RATE[i]/100});
            }
        }
        return tax;
    }
    void printTaxByTaxRate(long income) {
        ArrayList<long[]> tax = calTaxByTaxRate(income);
        long sum = 0;
        for (int i = 0; i < tax.size(); i++) {
            long[] taxInfo = tax.get(i);
            sum += taxInfo[1];
            System.out.printf("%10d * %2d%% = %15d\n",taxInfo[0], TAX_RATE[i], taxInfo[1]);
        }
        System.out.println();
        System.out.printf("[세율에 의한 세금]:%23d\n",sum);
    }
    private long calTaxByProgressiveDeductions(long income) {
        long tax = 0;
        for (int i = 1; i < TAX_RATE.length; i++) {
            if (income <= TAX_BASE[i]) {
                tax = (income*TAX_RATE[i-1]/100) - PROGRESSIVE_DEDUCTIONS[i-1];
                break;
            } else if (i == TAX_RATE.length-1) {
                tax = (income*TAX_RATE[i]/100) - PROGRESSIVE_DEDUCTIONS[i];
            }
        }
        return tax;
    }
    void printTaxByProgressiveDeductions(long income) {
        System.out.printf("[누진공제 계산에 의한 세금]:%15d\n", calTaxByProgressiveDeductions(income));
    }
}


