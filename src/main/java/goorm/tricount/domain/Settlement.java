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
}
