package Chess.model.dto;

public class OfflineScore {
    private int white;
    private int black;

    public OfflineScore() {
    }

    public OfflineScore(int white, int black) {
        this.white = white;
        this.black = black;
    }

    public int getWhite() {
        return white;
    }

    public void setWhite(int white) {
        this.white = white;
    }

    public int getBlack() {
        return black;
    }

    public void setBlack(int black) {
        this.black = black;
    }
}
