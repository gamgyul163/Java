package Mission01;

import java.util.*;

public class Task02 {
    public static void main(String[] args) {
        DistanceCalculator calculator = new DistanceCalculator();
        Position myPosition = calculator.createPosition();


        HashSet<Position> positions = new HashSet<>();

        while(positions.size()<10) {
            System.out.printf("입력된 좌표:%d개\n",positions.size());
            Position newPosition = calculator.createPosition();
            if (!positions.add(newPosition)) {
                System.out.println("중복된 좌표입니다.");
            }
        }
        System.out.print("가장 가까운 좌표:");
        calculator.getNearestPosition(myPosition, positions).printPos();
    }
}
class Position {
    private int x;
    private int y;
    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return this.getX() == position.getX() && this.getY() == position.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public void printPos() {
        System.out.println(x+" "+y);
    }
}
class DistanceCalculator {
    static final Scanner SCANNER = new Scanner(System.in);

    private final String INPUT_ERROR_MESSAGE = "잘못된 입력입니다. 다시 입력해주세요";


    Position createPosition() {
        int x = askNumericInput("x좌표을 입력해 주세요:");
        int y = askNumericInput("y좌표을 입력해 주세요:");
        return new Position(x,y);
    }
    private int askNumericInput(String prompt) { // 숫자 입력 유도 및 유효성 검증
        int numericInput;
        do {
            System.out.print(prompt);
            String strInput = SCANNER.nextLine().trim();
            if(strInput.matches("\\d+")) {
                numericInput = Integer.parseInt(strInput);
                break;
            }
            System.out.println(INPUT_ERROR_MESSAGE);
        } while (true);
        return numericInput;
    }
    double getDistance(Position position1, Position position2) {
        return Math.sqrt(Math.pow(position1.getX()- position2.getX(),2)+Math.pow(position1.getY()- position2.getY(),2));
    }
    Position getNearestPosition(Position myPosition, HashSet<Position> Positions) {
        Position nearestPosition = null;
        double minDistance = Double.MAX_VALUE;
        for (Position otherPosition:Positions) {
            double distance = getDistance(myPosition,otherPosition);
            if (distance<minDistance) {
                nearestPosition = otherPosition;
                minDistance = distance;
            }
        }
        return nearestPosition;
    }
}