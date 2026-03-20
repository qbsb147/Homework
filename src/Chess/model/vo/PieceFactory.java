package Chess.model.vo;

import Chess.model.vo.piece.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PieceFactory {
    private final Map<Character, Piece> pieceMap;

    public PieceFactory(List<Piece> pieces){
        pieceMap = pieces.stream()
                .collect(Collectors.toMap(
                    Piece::getKey,
                    p -> p
        ));
    }

    public Piece getPiece(char piece){
        return pieceMap.get(piece);
    }
}
