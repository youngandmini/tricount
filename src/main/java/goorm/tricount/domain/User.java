package goorm.tricount.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String username;    //아이디
    private String password;    //패스워드
    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<UserSettlement> userSettlementList = new ArrayList<>();

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
}
