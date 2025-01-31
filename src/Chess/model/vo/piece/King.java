package Chess.model.vo.piece;

import Chess.model.vo.Piece;

public class King extends Piece {
    @Override
    public boolean movable(String prePos, String nextPos, String[][] position) {
        start = position[7-(prePos.charAt(0) - '1')][prePos.charAt(1) - 'A'];
        destination = position[7 - (nextPos.charAt(0) - '1')][nextPos.charAt(1) - 'A'];

        //1사분면, 3사분면
        if (Math.abs(nextPos.charAt(0)-prePos.charAt(0))<=1&&Math.abs(nextPos.charAt(1)-prePos.charAt(1))<=1) return true;
        return false;
    }
}
