package Chess.model.vo;

public abstract class Piece {
    protected String start;
    protected String destination;

    abstract public boolean movable(String prePos, String nextPos, String[][] position);
}
