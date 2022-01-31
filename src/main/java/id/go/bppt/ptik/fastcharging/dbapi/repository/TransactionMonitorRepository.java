package id.go.bppt.ptik.fastcharging.dbapi.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import id.go.bppt.ptik.fastcharging.dbapi.model.TransactionMonitor;

@Repository
public interface TransactionMonitorRepository extends JpaRepository<TransactionMonitor, Long>{

	@Query(value = "SELECT * FROM transaction_monitor", countQuery = "SELECT COUNT(*) FROM transaction_monitor", nativeQuery = true)
	public Page<Map<String, Object>> findAllTransactionMonitor(Pageable pageable);

	List<TransactionMonitor> findByIdTagOrderByStopTimestampDesc(String idTag, Pageable pageable);

	List<TransactionMonitor> findByTransactionPk(int transactionPk);
}
