package id.go.bppt.ptik.fastcharging.dbapi.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import id.go.bppt.ptik.fastcharging.dbapi.model.ConnectorStatus;

public interface ConnectorStatusRepository extends JpaRepository<ConnectorStatus, Long>, JpaSpecificationExecutor<ConnectorStatus> {

	@Query(value = "SELECT * FROM connector_status_latest", countQuery = "SELECT COUNT(*) FROM connector_status_latest", nativeQuery = true)
	public Page<Map<String, Object>> findAllConnectorStatus(Pageable pageable);
	
	public List<ConnectorStatus> findAll() ;
	
	public List<ConnectorStatus> findAllConnectorStatusByConnectorId(int connectorId);
	public List<ConnectorStatus> findAllConnectorStatusByConnectorPk(int connectorPk);

	public ConnectorStatus findByChargeBoxIdAndConnectorId(String chargeBoxId, int connectorId);
}
