package id.go.bppt.ptik.fastcharging.dbapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Immutable
@Table(name="transaction_monitor")
@Subselect("select uuid() as id, tm.* from transaction_monitor tm")
public class TransactionMonitor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9220604655592522190L;

	@Id
    private String id;
	
	@Column(name="transaction_pk")
	private int transactionPk;
	
	@Column(name="connector_pk")
	private int connectorPk;
	
	@Column(name="start_value")
	private String startValue;
	
	@Column(name="stop_value")
	private String stopValue;
	
	@Column(name="start_timestamp")
	private String startTimestamp;
	
	@Column(name="stop_timestamp")
	private String stopTimestamp;
	
	@Column(name="connector_id")
	private int connectorId;
	
	@Column(name="NameCS")
	private String CSName;
	
	@Column(name="namecon")
	private String connectorName;
	
	@Column(name="ID_TAG")
	private String idTag;
	
	@Column(name="Namecust")
	private String customerName;
	
	@Column(name="city")
	private String city;
}
