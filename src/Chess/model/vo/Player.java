package Chess.model.vo;

import java.util.Objects;

public class Player {
    private long userNo;
    private String id;
    private String pwd;
    private String name;
    private int age;
    private String gender;
    private String email;
    private String phone;

    public Player() {
    }

    public Player(Long userNo, String id, String pwd, String name, int age, String gender, String email, String phone) {
        this.userNo = userNo;
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }

    public long getUserNo() {
        return userNo;
    }

    public String getId() {
        return id;
    }

    public String getPwd() {
        return pwd;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(userNo, player.userNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userNo);
    }
}
