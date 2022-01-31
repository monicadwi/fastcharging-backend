package id.go.bppt.ptik.fastcharging.dbapi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.SortDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;

import id.go.bppt.ptik.fastcharging.dbapi.model.ConnectorStatus;
import id.go.bppt.ptik.fastcharging.dbapi.model.ConnectorStatusDistance;
import id.go.bppt.ptik.fastcharging.dbapi.repository.ConnectorStatusRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConnectorStatusServiceImpl implements ConnectorStatusService {

	@Autowired
	private ConnectorStatusRepository connectorStatusRepository;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	GeoApiContext myContext;

	@Override
	public List<Map<String, Object>> findAllConnectorStatus(int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);

		Page<Map<String, Object>> pagedResult = connectorStatusRepository.findAllConnectorStatus(paging);

		return pagedResult.toList();
	}

	private Specification<ConnectorStatus> plugTypeIn(List<Integer> connectorIds) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("connectorId")).value(connectorIds);
	}

//	private Specification<ConnectorStatus> csInRange(long range_meter, String userLoc)
//	{
//		return (root, query, criteriaBuilder) -> {
//			List<String> list = Arrays.asList(root.get("latitude").toString(), root.get("longitude").toString());
//			String csLoc = String.join(",", list);
//			
//			criteriaBuilder.lessThanOrEqualTo(root.get(map), range_meter);		
//			} 
//		
//	}

	@Override
	public List<ConnectorStatus> findAll() {
		List<Integer> plugTypes = new ArrayList<Integer>();
		plugTypes.add(2);
		plugTypes.add(3);

		return connectorStatusRepository.findAll(plugTypeIn(plugTypes));
	}

	private DistanceMatrix getMatrix(String origins, List<String> destinations) {
		String[] destArray = destinations.toArray(new String[0]);

		try {
			return DistanceMatrixApi.getDistanceMatrix(myContext, new String[] { origins }, destArray).await();
		} catch (ApiException e) {
			log.error(e.getMessage());
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;

	}

	@Override
	public DistanceMatrix getMatrix(String origin) {
		List<ConnectorStatus> connectorStatuses = connectorStatusRepository.findAll();

		List<String> coordinates = connectorStatuses.stream().map(cs -> cs.getCoordinate())
				.collect(Collectors.toList());

		DistanceMatrix dm = getMatrix(origin, coordinates);

		return dm;
	}

	@Override
	public List<ConnectorStatusDistance> findConnectorStatusFilteredv2(long rangeMax, String origin, int pageNo,
			int pageSize) {
		List<ConnectorStatus> connectorStatuses = connectorStatusRepository.findAll();

		List<String> coordinates = connectorStatuses.stream().map(cs -> cs.getCoordinate())
				.collect(Collectors.toList());

		DistanceMatrix dm = getMatrix(origin, coordinates);

		int len = dm.rows[0].elements.length;

		List<ConnectorStatusDistance> connectorStatusDistances = new ArrayList<ConnectorStatusDistance>();
		for (int i = 0; i < len; i++) {
			connectorStatusDistances.add(new ConnectorStatusDistance(connectorStatuses.get(i),
					dm.rows[0].elements[i].distance.inMeters, dm.rows[0].elements[i].distance.humanReadable,
					dm.rows[0].elements[i].duration.inSeconds, dm.rows[0].elements[i].duration.humanReadable));
		}

		List<ConnectorStatusDistance> csDists = connectorStatusDistances.stream()
				.filter(csd -> csd.getDistance() <= rangeMax).collect(Collectors.toList());

		PagedListHolder<ConnectorStatusDistance> page = new PagedListHolder<ConnectorStatusDistance>(csDists);

		SortDefinition msd = new MutableSortDefinition("distance", true, true);
		page.setSort(msd);
		page.resort();

		page.setPage(pageNo);
		page.setPageSize(pageSize);

		return page.getPageList();
	}

	@Override
	public List<ConnectorStatusDistance> findConnectorStatusFilteredv3(long durationMax, String origin, int pageNo,
			int pageSize) {
		List<ConnectorStatus> connectorStatuses = connectorStatusRepository.findAll();

		List<String> coordinates = connectorStatuses.stream().map(cs -> cs.getCoordinate())
				.collect(Collectors.toList());

		DistanceMatrix dm = getMatrix(origin, coordinates);

		int len = dm.rows[0].elements.length;

		List<ConnectorStatusDistance> connectorStatusDistances = new ArrayList<ConnectorStatusDistance>();
		for (int i = 0; i < len; i++) {
			connectorStatusDistances.add(new ConnectorStatusDistance(connectorStatuses.get(i),
					dm.rows[0].elements[i].distance.inMeters, dm.rows[0].elements[i].distance.humanReadable,
					dm.rows[0].elements[i].duration.inSeconds, dm.rows[0].elements[i].duration.humanReadable));
		}

		List<ConnectorStatusDistance> csDists = connectorStatusDistances.stream()
				.filter(csd -> csd.getDuration() <= durationMax).collect(Collectors.toList());

		PagedListHolder<ConnectorStatusDistance> page = new PagedListHolder<ConnectorStatusDistance>(csDists);

		SortDefinition msd = new MutableSortDefinition("duration", true, true);
		page.setSort(msd);
		page.resort();

		page.setPage(pageNo);
		page.setPageSize(pageSize);

		return page.getPageList();
	}

	@Override
	public List<ConnectorStatusDistance> findConnectorStatusFilteredv4(long rangeMax, String origin, int pageNo,
			int pageSize, int plugType) {
		List<Integer> plugTypes = new ArrayList<Integer>();
		plugTypes.add(plugType);
		plugTypes.add(3);

		List<ConnectorStatus> connectorStatuses = connectorStatusRepository.findAll(plugTypeIn(plugTypes));

		List<String> coordinates = connectorStatuses.stream().map(cs -> cs.getCoordinate())
				.collect(Collectors.toList());

		DistanceMatrix dm = getMatrix(origin, coordinates);

		int len = dm.rows[0].elements.length;

		List<ConnectorStatusDistance> connectorStatusDistances = new ArrayList<ConnectorStatusDistance>();
		for (int i = 0; i < len; i++) {
			connectorStatusDistances.add(new ConnectorStatusDistance(connectorStatuses.get(i),
					dm.rows[0].elements[i].distance.inMeters, dm.rows[0].elements[i].distance.humanReadable,
					dm.rows[0].elements[i].duration.inSeconds, dm.rows[0].elements[i].duration.humanReadable));
		}

		List<ConnectorStatusDistance> csDists = connectorStatusDistances.stream()
				.filter(csd -> csd.getDistance() <= rangeMax).collect(Collectors.toList());

		PagedListHolder<ConnectorStatusDistance> page = new PagedListHolder<ConnectorStatusDistance>(csDists);

		SortDefinition msd = new MutableSortDefinition("distance", true, true);
		page.setSort(msd);
		page.resort();

		page.setPage(pageNo);
		page.setPageSize(pageSize);

		return page.getPageList();
	}

	@Override
	public ConnectorStatus findByChargeBoxIdAndConnectorId(String chargeBoxId, int connectorId) {
		return connectorStatusRepository.findByChargeBoxIdAndConnectorId(chargeBoxId, connectorId);

	}

}
