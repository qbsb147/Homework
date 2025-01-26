package Chess.service.piece;

public abstract class Piece {
    protected String start;
    protected String destination;

    abstract public boolean movable(String prePos, String nextPos, String[][] position);

    public String record(){
        return start + ":" + destination;
    };
}
