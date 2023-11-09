package goorm.tricount.repository.settlement;

import goorm.tricount.domain.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementJpaRepository extends JpaRepository<Settlement, Long> {

}
