package Chess.model.vo.builder;

import Chess.model.vo.Player;

public class PlayerBuilder {
    private long userNo;
    private String id;
    private String pwd;
    private String name;
    private int age;
    private String gender;
    private String email;
    private String phone;

    public PlayerBuilder() {

    }

    public PlayerBuilder userNo(long userNo) {
        this.userNo = userNo;
        return this;
    }

    public PlayerBuilder id(String id) {
        this.id = id;
        return this;
    }

    public PlayerBuilder pwd(String pwd) {
        this.pwd = pwd;
        return this;
    }

    public PlayerBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PlayerBuilder age(int age) {
        this.age = age;
        return this;
    }

    public PlayerBuilder gender(String gender) {
        this.gender = gender;
        return this;
    }

    public PlayerBuilder email(String email) {
        this.email = email;
        return this;
    }

    public PlayerBuilder phone(String phone) {
        this.phone = phone;
        return this;
    }

    public Player build() {
        return new Player(userNo, id, pwd, name, age, gender, email, phone); // Player 생성자 호출
    }
}