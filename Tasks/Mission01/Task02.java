package Mission01;

public class Task02 {
}
class Pos {
    int x;
    int y;
    Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public boolean equals(Pos pos) {
        if (pos.x == this.x && pos.y == this.y) {
            return true;
        }
        return false;
    }
}