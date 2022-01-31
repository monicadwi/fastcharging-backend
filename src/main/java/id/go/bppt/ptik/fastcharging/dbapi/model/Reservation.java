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
@Table(name = "reservation")
@Getter @Setter
public class Reservation {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationPk;

    private int connectorPk;
    private Long transaction_pk;
    private String idTag;
    private Date start_datetime;
    private Date expiry_datetime;
    private String status;

}
