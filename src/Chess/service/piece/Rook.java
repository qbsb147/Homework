package Chess.service.piece;

import Chess.service.Piece;

public class Rook implements Piece {
    @Override
    public String move(String prePos, String nextPos, String[][] position) {
        String me = position[prePos.charAt(0) - '1'][7 - (prePos.charAt(1) - 'A')];
        String aggre = position[prePos.charAt(0) - '1'][7 - (prePos.charAt(1) - 'A')];

        int sub=0;

        if(prePos.charAt(0)!=nextPos.charAt(0)&&prePos.charAt(1)!=nextPos.charAt(1))return "--";
        if (prePos.charAt(0) == nextPos.charAt(0)) {
            while(sub!=nextPos.charAt(0) - prePos.charAt(0)){

            }
        }

        public int[] moveWight(position, int[] point, int move){
            if(prePos.charAt(0)>=nextPos.charAt(0))return point;
            for (int i = point[1]; i<=point[1]+move; i++) if(position[point[0]][i]=='X') return point;
            point[1] += move;
            return point;
        }

        public int[] moveHight(position, int[] point, int move){
            if(point[0]+move>=position.length)return point;
            for(int i = point[0]; i<=point[0]+move; i++)if(position[i][point[1]]=='X') return point;
            point[0] += move;
            return point;
        }
    }
}
