package Chess.model.vo;

public class Record {
    private Long gameNo;
    private Long userNo;
    private String id;
    private String victory;
    private String position;
    private String record;

    public Record() {
    }

    public Record(Long gameNo, Long userNo, String id, String victory, String position, String record) {
        this.gameNo = gameNo;
        this.userNo = userNo;
        this.id = id;
        this.victory = victory;
        this.position = position;
        this.record = record;
    }

    public Long getGameNo() {
        return gameNo;
    }

    public Long getUserNo() {
        return userNo;
    }

    public String getId() {
        return id;
    }

    public String getVictory() {
        return victory;
    }

    public String getPosition() {
        return position;
    }

    public String getRecord() {
        return record;
    }

    @Override
    public String toString() {
        return "Record{" +
                "gameNo=" + gameNo +
                ", userNo=" + userNo +
                ", id='" + id + '\'' +
                ", victory='" + victory + '\'' +
                ", position='" + position + '\'' +
                ", record='" + record + '\'' +
                '}';
    }
}
