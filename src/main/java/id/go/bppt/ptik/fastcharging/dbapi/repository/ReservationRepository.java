package id.go.bppt.ptik.fastcharging.dbapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import id.go.bppt.ptik.fastcharging.dbapi.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{

	public Reservation findAllByReservationPk(Long reservationPk);

	@Query(value = "SELECT \r\n" + 
			"  rsvp.reservation_pk as reservationPk, \r\n" + 
			"  rsvp.connector_pk as connectorPk, \r\n" + 
			"  cn.charge_box_id as chargeBoxId, \r\n" + 
			"  rsvp.id_tag as idTag, \r\n" + 
			"  rsvp.start_datetime, \r\n" + 
			"  rsvp.expiry_datetime, \r\n" + 
			"  rsvp.status, \r\n" + 
			"  cn.connector_custom as connectorId, \r\n" + 
			"  mcs.name AS NameCS, \r\n" + 
			"  mcs.location, \r\n" + 
			"  mcs.longitude, \r\n" + 
			"  mcs.latitude, \r\n" + 
			"  mct.city AS City \r\n" + 
			"FROM \r\n" + 
			"  reservation rsvp \r\n" + 
			"  INNER JOIN connector_custom cn ON rsvp.connector_pk = cn.connector_pk \r\n" + 
			"  INNER JOIN mdb_cs mcs ON (\r\n" + 
			"    cn.CHARGE_BOX_ID collate utf8_general_ci\r\n" + 
			"  )=(\r\n" + 
			"    `mcs`.`CHARGE_BOX_ID` collate utf8_general_ci\r\n" + 
			"  ) \r\n" + 
			"  INNER JOIN mdb_city mct ON mcs.city = mct.id \r\n" + 
			"  INNER JOIN connector_max_status_timestamp_v4 cmax ON rsvp.connector_pk = cmax.connector_pk \r\n" + 
			"WHERE \r\n" + 
			"  rsvp.status = 'ACCEPTED' \r\n" + 
			"  AND cmax.status = 'Reserved' \r\n" + 
			"  AND TIMESTAMPDIFF(\r\n" + 
			"    SECOND, NOW() + INTERVAL 7 HOUR, rsvp.expiry_datetime\r\n" + 
			"  )>= 0 \r\n" + 
			"  AND rsvp.id_tag = ? \r\n" + 
			"ORDER BY \r\n" + 
			"  rsvp.reservation_pk DESC \r\n" + 
			"limit \r\n" + 
			"  1;\r\n" + 
			"", nativeQuery = true)
	public List<Map<String, Object>> findReservationByReservationPkAvailable(String idTag);

}
