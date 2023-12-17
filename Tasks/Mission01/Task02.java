package Mission01;

import java.util.*;

public class Task02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PositionManager manager = new PositionManager(scanner);
        manager.setMyPosition();
        manager.setPositions(10);
        System.out.print("가장 가까운 좌표:");
        manager.getNearestPosition().printPos();
    }
}
class Position {
    private int x;
    private int y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Position position = (Position) obj;
        return this.x == position.x && this.y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public void printPos() {
        System.out.println(x+" "+y);
    }

    public double getDistance(Position other) {
        return Math.sqrt(Math.pow(this.x- other.x,2)+Math.pow(this.y- other.y,2));
    }
}

class PositionManager {


    private static final String INPUT_ERROR_MESSAGE = "잘못된 입력입니다. 다시 입력해주세요";
    private static final String DUPLICATE_ERROR_MESSAGE = "잘못된 입력입니다. 다시 입력해주세요";
    private static final String Request_X = "x좌표을 입력해 주세요:";
    private static final String Request_Y = "y좌표을 입력해 주세요:";
    private Position myPosition;
    private HashSet<Position> positions;
    private Scanner scanner;

    PositionManager(Scanner scanner) {
        this.scanner = scanner;
    }

    private Position createPosition() {
        int x = askNumericInput(Request_X);
        int y = askNumericInput(Request_Y);
        return new Position(x,y);
    }
    public void setMyPosition() {
        this.myPosition = createPosition();
    }
    public void setPositions(int size) {
        this.positions = new HashSet<>();
        while (this.positions.size() < size) {
            System.out.printf("입력된 좌표:%d개\n",positions.size());
            if (!positions.add(createPosition())) {
                System.out.println(DUPLICATE_ERROR_MESSAGE);
            }
        }
    }

    private int askNumericInput(String prompt) {
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

    Position getNearestPosition() {
        Position nearestPosition = null;
        double minDistance = Double.MAX_VALUE;
        for (Position otherPosition:positions) {
            double distance = myPosition.getDistance(otherPosition);
            if (distance<minDistance) {
                nearestPosition = otherPosition;
                minDistance = distance;
            }
        }
        return nearestPosition;
    }
}