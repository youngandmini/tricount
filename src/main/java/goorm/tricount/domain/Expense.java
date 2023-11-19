package goorm.tricount.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private Long id;

    private String name;
    private BigDecimal price;
    private LocalDate expensedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User paidBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settlement_id")
    private Settlement settlement;

    public Expense(String name, BigDecimal price, LocalDate expensedDate, User paidBy, Settlement settlement) {
        this.name = name;
        this.price = price;
        this.expensedDate = expensedDate;
        this.paidBy = paidBy;
        this.settlement = settlement;
        settlement.getExpenseList().add(this);
    }
}
