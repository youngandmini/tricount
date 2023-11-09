package goorm.tricount.domain;


import jakarta.persistence.*;

@Entity
public class UserSettlement {

    @Id
    @GeneratedValue
    @Column(name = "user_settlement_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settlement_id")
    private Settlement settlement;
}
