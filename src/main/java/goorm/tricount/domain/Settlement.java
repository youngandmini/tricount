package goorm.tricount.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Settlement {

    @Id
    @GeneratedValue
    @Column(name = "settlement_id")
    private Long id;

    @OneToMany(mappedBy = "settlement")
    private List<Expense> expenseList = new ArrayList<>();

    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "settlement")
    private List<UserSettlement> userSettlementList = new ArrayList<>();

    public Settlement(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public void setOwner(User user) {
        this.owner = owner;
    }
}
