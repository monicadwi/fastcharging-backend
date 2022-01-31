package id.go.bppt.ptik.fastcharging.dbapi.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Immutable
@Table(name="connector_status_latest")
@Subselect("select uuid() as id, csl.* from connector_status_latest csl")
public class ConnectorStatus implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2431037367660239010L;
	
	@Id
    private String id;
	
	@Column(name="charge_box_pk")
	private int chargeBoxPk;
	
	@Column(name="charge_box_id")
	private String chargeBoxId;
	
	@Column(name="connector_pk")
	private int connectorPk;
	
	private LocalDateTime max_status_timestamp;
	
	@Column(name="connector_id")
	private int connectorId;
	
	@Column(name="plug_type")
	private String plugType;
	
	private String name;
	private String latitude;
	private String longitude;
	private LocalDateTime last_heartbeat_timestamp_gmt7;
	@Transient
	private String coordinate;
	public String getCoordinate() {
		return latitude.concat(",").concat(longitude);
	}
	
}
