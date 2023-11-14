package goorm.tricount.repository.usersettlement;

import goorm.tricount.domain.Settlement;
import goorm.tricount.domain.User;
import goorm.tricount.domain.UserSettlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserSettlementJpaRepository extends JpaRepository<UserSettlement, Long> {

    @Query("select u from UserSettlement us join fetch us.user u where us.settlement.id = :settlementId")
    List<User> findUsersBySettlementId(@Param("settlementId") Long settlementId);

    @Query("select s from UserSettlement us join fetch us.settlement s where us.user.id = :userId")
    List<Settlement> findSettlementsByUserId(@Param("userId") Long userId);

}
