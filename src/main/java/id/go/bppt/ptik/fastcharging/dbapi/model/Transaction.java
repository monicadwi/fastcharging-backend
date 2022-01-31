package id.go.bppt.ptik.fastcharging.dbapi.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transaction")
@Getter @Setter
public class Transaction {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transaction_pk;

    private Long connector_pk;
    private String id_tag;
    private Date start_event_timestamp;
    private Date start_timestamp;
    private String start_value;
    private Date stop_event_actor;
    private Date stop_event_timestamp;
    private String stop_timestamp;
    private String stop_value;
    private String stop_reason;
}
