package Chess.service.piece;

public class Pawn extends Piece {
    @Override
    public boolean movable(String prePos, String nextPos, String[][] position) {
        start = position[7-(prePos.charAt(0) - '1')][prePos.charAt(1) - 'A'];
        destination = position[7 - (nextPos.charAt(0) - '1')][nextPos.charAt(1) - 'A'];

        if(prePos.charAt(0)==nextPos.charAt(0))return false;

        //전진
        if(prePos.charAt(1)==nextPos.charAt(1)){
            if(start.startsWith("b")){        // 1칸 전진
                if(prePos.charAt(0) - nextPos.charAt(0)==1){
                    if(destination==null) return true;
                }        // 2칸 전진
                if (prePos.startsWith("2") && nextPos.startsWith("4")) {
                    if (position[7 - (prePos.charAt(0) - '1') - 1][prePos.charAt(1) - 'A'] == null
                            && destination == null) return true;
                }
            }else {        // 1칸 전진
                if (nextPos.charAt(0) - prePos.charAt(0) == 1) {
                    if (destination == null) return true;
                }        // 2칸 전진
                if (prePos.startsWith("7") && nextPos.startsWith("5")) {
                    if (position[7 - (prePos.charAt(0) - '1') + 1][prePos.charAt(1) - 'A'] == null
                            && destination == null) return true;
                }
            }
        }

        //공격

        if(start.startsWith("b")){
            if(nextPos.charAt(0)-prePos.charAt(0)==1
                &&(Math.abs(prePos.charAt(1)-nextPos.charAt(1))==1))
                if(destination!=null&&destination.startsWith("w"))return true;
        }else{
            if(prePos.charAt(0)-nextPos.charAt(0)==1
                &&(Math.abs(nextPos.charAt(1)-prePos.charAt(1))==1))
                if(destination!=null&&destination.startsWith("b"))return true;
        }
        return false;
    }

    @Override
    public String record() {
        return super.record();
    }
}
