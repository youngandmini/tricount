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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User owner;

    public Settlement(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public void setOwner(User user) {
        this.owner = owner;
    }
}
