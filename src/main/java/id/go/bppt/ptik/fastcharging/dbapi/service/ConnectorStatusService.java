package id.go.bppt.ptik.fastcharging.dbapi.service;

import java.util.List;
import java.util.Map;

import com.google.maps.model.DistanceMatrix;

import id.go.bppt.ptik.fastcharging.dbapi.model.ConnectorStatus;
import id.go.bppt.ptik.fastcharging.dbapi.model.ConnectorStatusDistance;

public interface ConnectorStatusService {
	List<Map<String,Object>> findAllConnectorStatus(int pageNo, int pageSize);
	
	List<ConnectorStatus> findAll();
	
	List<ConnectorStatusDistance> findConnectorStatusFilteredv2(long rangeMax, String origin, int pageNo, int pageSize);

	List<ConnectorStatusDistance> findConnectorStatusFilteredv3(long durationMax, String origin, int pageNo, int pageSize);

	List<ConnectorStatusDistance> findConnectorStatusFilteredv4(long rangeMax, String origin, int pageNo, int pageSize, int plugType);
	
	ConnectorStatus findByChargeBoxIdAndConnectorId(String chargeBoxId, int connectorId);
	
	DistanceMatrix getMatrix(String origin);
}
