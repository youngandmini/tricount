package goorm.tricount.domain;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Settlement {

    @Id
    @GeneratedValue
    @Column(name = "settlement_id")
    private Long id;

    @OneToMany(mappedBy = "settlement")
    private List<Expense> expenses = new ArrayList<>();
}
