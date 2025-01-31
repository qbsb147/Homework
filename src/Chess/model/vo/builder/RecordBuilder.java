package Chess.model.vo.builder;

import Chess.model.vo.Record;

public class RecordBuilder {
    private Long gameNo;
    private Long userNo;
    private String id;
    private String victory;
    private String position;
    private String record;

    public RecordBuilder() {

    }

    public RecordBuilder gameNo(long gameNo) {
        this.gameNo = gameNo;
        return this;
    }

    public RecordBuilder userNo(long userNo) {
        this.userNo = userNo;
        return this;
    }

    public RecordBuilder id(String id) {
        this.id = id;
        return this;
    }

    public RecordBuilder victory(String victory) {
        this.victory = victory;
        return this;
    }

    public RecordBuilder position(String position) {
        this.position = position;
        return this;
    }

    public RecordBuilder record(String record) {
        this.record = record;
        return this;
    }

    public Record build() {
        return new Record(gameNo, userNo, id, victory, position, record);
    }
}