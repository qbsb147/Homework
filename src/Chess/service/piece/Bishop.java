package Chess.service.piece;

public class Bishop extends Piece {
    @Override
    public boolean movable(String prePos, String nextPos, String[][] position) {
        start = position[7-(prePos.charAt(0) - '1')][prePos.charAt(1) - 'A'];
        destination = position[7 - (nextPos.charAt(0) - '1')][nextPos.charAt(1) - 'A'];

        //2사분면, 4사분면
        if ((nextPos.charAt(0) - prePos.charAt(0))==-(nextPos.charAt(1) - prePos.charAt(1))) {
            int sub = nextPos.charAt(0) - prePos.charAt(0);
            if(sub>0)sub--;
            else sub++; // 도착지에서 1칸 감소
            while(sub!=0){
                if((position[7 - (prePos.charAt(0) - '1')-sub][prePos.charAt(1)-'A'-sub] != null))return false;   //사이에 뭔가 있으면 멈춤
                if(sub>0)sub--;
                else sub++;
            }
            return true;
        }else if((nextPos.charAt(0) - prePos.charAt(0))==(nextPos.charAt(1) - prePos.charAt(1))){  // 1사분면, 3사분면
            int sub = nextPos.charAt(0) - prePos.charAt(0);
            if(sub>0)sub--;
            else sub++; //도착지에서 1칸 감소
            while(sub!=0){
                if((position[7 - (prePos.charAt(0) - '1')-sub][prePos.charAt(1) - 'A'+sub] != null))return false;   //사이에 뭔가 있으면 멈춤
                if(sub>0)sub--;
                else sub++;
            }
            return true;
        }else return false;
    }

    @Override
    public String record() {
        return super.record();
    }
}
