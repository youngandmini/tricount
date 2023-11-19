package goorm.tricount.repository.usersettlement;

import goorm.tricount.domain.UserSettlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserSettlementJpaRepository extends JpaRepository<UserSettlement, Long> {


    @Query("select us from UserSettlement us" +
            " where us.settlement.id = :settlementId" +
            " and us.user.id = :userId")
    Optional<UserSettlement> findBySettlementIdAndUserId(@Param("settlementId") Long settlementId, @Param("userId") Long userId);


    @Query("select us from UserSettlement us" +
            " join fetch us.settlement s" +
            " join fetch s.owner" +
            " where us.user.id = :userId")
    List<UserSettlement> findByUserId(@Param("userId") Long userId);
}
