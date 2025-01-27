package Chess.service.piece;

public class Queen extends Piece {
    @Override
    public boolean movable(String prePos, String nextPos, String[][] position) {
        start = position[7-(prePos.charAt(0) - '1')][prePos.charAt(1) - 'A'];
        destination = position[7 - (nextPos.charAt(0) - '1')][nextPos.charAt(1) - 'A'];

        // Rook의 이동 방식
        if(prePos.charAt(0)==nextPos.charAt(0)||prePos.charAt(1)==nextPos.charAt(1)){
            Rook rook = new Rook();
            return rook.movable(prePos, nextPos, position);
        }else{  //Bishop의 이동방식
            Bishop bishop = new Bishop();
            return bishop.movable(prePos, nextPos, position);
        }
    }

    @Override
    public String record() {
        return super.record();
    }
}
