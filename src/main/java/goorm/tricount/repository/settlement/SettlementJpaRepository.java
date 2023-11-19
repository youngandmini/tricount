package goorm.tricount.repository.settlement;

import goorm.tricount.domain.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SettlementJpaRepository extends JpaRepository<Settlement, Long> {

    @Query("select distinct s from Settlement s left join fetch s.expenseList where s.id = :settlementId")
    Optional<Settlement> findByIdWithExpense(@Param("settlementId") Long settlementId);


    @Query("select distinct s from Settlement s" +
            " left join fetch s.userSettlementList us" +
            " join fetch us.user" +
            " where s.id = :settlementId")
    Optional<Settlement> findByIdWithUserSettlement(@Param("settlementId") Long settlementId);
}
