package Chess.model.vo;

public class Record {
    private Long gameNo;
    private String id;
    private String victory;
    private String position;
    private String record;

    public Record() {
    }

    public Record(Long gameNo, String id, String victory, String position, String record) {
        this.gameNo = gameNo;
        this.id = id;
        this.victory = victory;
        this.position = position;
        this.record = record;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getGameNo() {
        return gameNo;
    }

    public void setGameNo(Long gameNo) {
        this.gameNo = gameNo;
    }

    public String getVictory() {
        return victory;
    }

    public void setVictory(String victory) {
        this.victory = victory;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

}
