package Chess.service;

public interface Piece {
    String move(String prePos, String nextPos, String[][] position);

    String[] record();
}
