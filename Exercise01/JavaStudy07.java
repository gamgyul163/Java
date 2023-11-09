// 백엔드 19기 임국희

import java.util.*;
import java.util.stream.Collectors;

public class JavaStudy07 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LotteryManager lotteryManager = new LotteryManager(scanner);

        System.out.printf("[로또 당첨 프로그램]\n\n");
        Lottery myLottery = lotteryManager.createLottery(lotteryManager.askForBuyingLottery());
        myLottery.printLottery();

        lotteryManager.printWinningGame();

        myLottery.printLotteryResult(lotteryManager.getWinningGame());
    }

}

class Lottery {
    private final Game[] games;

    Lottery(Game[] games) {
        this.games = games;
    }
    void printLottery() {
        for (Game game:games) {
            game.printGame();
            System.out.println();
        }
        System.out.println();
    }
    void printLotteryResult(Game winningGame) {
        System.out.println("[내 로또 결과]");
        for (Game game:games) {
            game.printResult(winningGame);
            System.out.println();
        }
        System.out.println();
    }

}
class Game{
    private final char gameID;
    private final TreeSet<Integer> numbers;

    Game(char gameID, TreeSet<Integer> numbers) {
        this.gameID = gameID;
        this.numbers = numbers;
    }
    void printGame() {
        String strNumbers = numbers.stream().map(n -> String.format("%02d",n)).collect(Collectors.joining(","));
        System.out.printf("%-4s%s", gameID, strNumbers);
    }

    private int countWinningNumbers(Game winningGame) {
        int count = 0;
        for (int num:winningGame.numbers) {
            if(numbers.contains(num)) {
                count++;
            }
        }
        return count;
    }
    void printResult(Game winningGame) {
        printGame();
        System.out.printf(" => %d개 일치",countWinningNumbers(winningGame));
    }
}

class LotteryManager {
    private Game winningGame;
    private final Scanner scanner;
    private final int NUMBER_OF_WINNING_NUMBERS = 6;
    private final int MAXIMUM_OF_WINNING_NUMBERS = 45;
    private final String INPUT_ERROR_MESSAGE = "잘못된 입력입니다. 다시 입력해주세요";

    LotteryManager(Scanner scanner) {
        this.scanner = scanner;
    }
    int askForBuyingLottery() {
        int numericInput;
        do {
            System.out.print("로또 개수를 입력해 주세요.(숫자 1 ~ 10):");
            String strInput = scanner.nextLine().trim();
            if(strInput.matches("\\d{1,2}")) { // 정규 표현식으로 자릿수 검사
                numericInput = Integer.parseInt(strInput);
                if (numericInput >= 1 && numericInput <= 10) {
                    break;
                }
            }
            System.out.println(INPUT_ERROR_MESSAGE);
        } while (true);
        return numericInput;
    }

    private TreeSet<Integer> createNumbers() {
        Random random = new Random();
        TreeSet<Integer> newNumbers = new TreeSet<>();
        while (newNumbers.size() < NUMBER_OF_WINNING_NUMBERS) {
            newNumbers.add(random.nextInt(MAXIMUM_OF_WINNING_NUMBERS)+1);
        }
        return newNumbers;
    }
    Lottery createLottery(int countOfGames) {
        return new Lottery(createGames(countOfGames));
    }
    private Game[] createGames(int countOfGames) {
        Game[] newGames = new Game[countOfGames];
        for (int i = 0; i < countOfGames; i++) {
            newGames[i] = new Game((char)('A'+i),createNumbers());
        }
        return newGames;
    }
    private void createWinningGame() {
        winningGame = new Game(' ', createNumbers());
    }
    void printWinningGame() {
        if (winningGame == null) {
            createWinningGame();
        }
        System.out.println("[로또 발표]");
        winningGame.printGame();
        System.out.println("\n");
    }
    Game getWinningGame() {
        return winningGame;
    }
}