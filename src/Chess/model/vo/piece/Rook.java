package Chess.model.vo.piece;

import Chess.model.vo.Piece;

public class Rook extends Piece {
    @Override
    public boolean movable(String prePos, String nextPos, String[][] position) {
        start = position[7-(prePos.charAt(0) - '1')][prePos.charAt(1) - 'A'];
        destination = position[7 - (nextPos.charAt(0) - '1')][nextPos.charAt(1) - 'A'];

        if(prePos.charAt(0)!=nextPos.charAt(0)&&prePos.charAt(1)!=nextPos.charAt(1))return false;   //가로, 세로 둘 다 같은 것이 없을 때
        //가로축
        if (prePos.charAt(0) == nextPos.charAt(0)) {
            int sub = nextPos.charAt(1) - prePos.charAt(1);
            if(sub>0)sub--;
            else sub++; // 도착지에서 1칸 감소
            while(sub!=0){
                if((position[7 - (prePos.charAt(0) - '1')][prePos.charAt(1)+sub] != null))return false;   //사이에 뭔가 있으면 멈춤
                if(sub>0)sub--;
                else sub++;
            }
        }else{  // 세로축
            int sub = nextPos.charAt(0) - prePos.charAt(0);
            if(sub>0)sub--;
            else sub++; //도착지에서 1칸 감소
            while(sub!=0){
                if((position[7 - (prePos.charAt(0) - '1')-sub][prePos.charAt(1) - 'A'] != null))return false;   //사이에 뭔가 있으면 멈춤
                if(sub>0)sub--;
                else sub++;
            }
        }
        return true;
    }
}
