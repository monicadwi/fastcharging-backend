package id.go.bppt.ptik.fastcharging.dbapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConnectorStatusDistance {
	private ConnectorStatus connectorStatus;
	private long distance;
	private String distanceReadable;
	private long duration;
	private String durationReadable;
}
