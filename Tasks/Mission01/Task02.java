package Mission01;

// 백엔드 19기 임국희

import java.util.*;

public class Task02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PositionManager manager = new PositionManager(scanner);
        manager.setMyPosition();
        manager.setPositions(10);
        manager.getNearestPosition();
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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


    private static final String INPUT_ERROR_MESSAGE = "잘못된 입력입니다. 다시 입력해주세요.";
    private static final String DUPLICATE_ERROR_MESSAGE = "동일한 좌표값이 이미 존재합니다. 다시 입력해 주세요.";
    private Position myPosition;
    private LinkedHashSet<Position> positions;
    private Scanner scanner;

    PositionManager(Scanner scanner) {
        this.scanner = scanner;
    }

    private Position createPosition(String target) {
        int x = askNumericInput(String.format("%s x값을 입력해 주세요.", target));
        int y = askNumericInput(String.format("%s y값을 입력해 주세요.", target));
        return new Position(x,y);
    }
    public void setMyPosition() {
        this.myPosition = createPosition("내 좌표");
    }
    public void setPositions(int size) {
        this.positions = new LinkedHashSet<>();
        while (this.positions.size() < size) {
            System.out.printf("%d/%d 번째 입력\n",positions.size()+1, size);
            if (!positions.add(createPosition("임의의 좌표"))) {
                System.out.println(DUPLICATE_ERROR_MESSAGE);
            }
        }
    }

    private int askNumericInput(String prompt) {
        int numericInput;
        do {
            System.out.println(prompt);
            String strInput = scanner.nextLine().trim();
            if(strInput.matches("\\d+")) {
                numericInput = Integer.parseInt(strInput);
                break;
            }
            System.out.println(INPUT_ERROR_MESSAGE);
        } while (true);
        return numericInput;
    }

    void getNearestPosition() {
        Position nearestPosition = null;
        double minDistance = Double.MAX_VALUE;
        for (Position otherPosition:positions) {
            double distance = myPosition.getDistance(otherPosition);
            System.out.printf("(%d, %d) => %f\n",otherPosition.getX(), otherPosition.getY(),distance);
            if (distance<minDistance) {
                nearestPosition = otherPosition;
                minDistance = distance;
            }
        }
        System.out.println("제일 가까운 좌표:");
        System.out.printf("(%d, %d) => %f\n",nearestPosition.getX(), nearestPosition.getY(),minDistance);
    }
}