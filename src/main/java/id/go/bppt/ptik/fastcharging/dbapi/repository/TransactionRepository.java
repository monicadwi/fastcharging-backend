package id.go.bppt.ptik.fastcharging.dbapi.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import id.go.bppt.ptik.fastcharging.dbapi.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query(value = "SELECT trx.transaction_pk, trx.connector_pk, trx.start_value, trx.stop_value, "
			+ "mcs.name AS NameCS , mcust.ID AS IdCust, mcust.name AS NameCust, mcity.city " + "FROM transaction trx "
			+ "INNER JOIN connector cn ON cn.connector_pk = trx.connector_pk "
			+ "INNER JOIN mdb_cs mcs ON binary mcs.charge_box_id = binary cn.charge_box_id "
			+ "INNER JOIN mdb_customer mcust ON binary mcust.id_tag = binary trx.id_tag "
			+ "INNER JOIN mdb_city mcity ON mcs.city = mcity.id "
			+ "ORDER BY trx.transaction_pk DESC", countQuery = "select COUNT(*) FROM transaction trx INNER JOIN connector cn ON cn.connector_pk = trx.connector_pk INNER JOIN mdb_cs mcs ON binary mcs.charge_box_id = binary cn.charge_box_id INNER JOIN mdb_customer mcust ON binary mcust.id_tag = binary trx.id_tag INNER JOIN mdb_city mcity ON mcs.city = mcity.id", nativeQuery = true)
	public Page<Map<String, Object>> findAllTransaction(Pageable pageable);

	@Query(value = "SELECT trx.transaction_pk, trx.connector_pk, trx.start_value, trx.stop_value, "
			+ "mcs.name AS NameCS , mcust.ID AS IdCust, mcust.name AS NameCust, mcity.city " + "FROM transaction trx "
			+ "INNER JOIN connector cn ON cn.connector_pk = trx.connector_pk "
			+ "INNER JOIN mdb_cs mcs ON binary mcs.charge_box_id = binary cn.charge_box_id "
			+ "INNER JOIN mdb_customer mcust ON binary mcust.id_tag = binary trx.id_tag "
			+ "INNER JOIN mdb_city mcity ON mcs.city = mcity.id "
			 + "WHERE mcust.id_tag = ?1 "
			+ "ORDER BY trx.transaction_pk DESC", countQuery = "select COUNT(*) FROM transaction trx INNER JOIN connector cn ON cn.connector_pk = trx.connector_pk INNER JOIN mdb_cs mcs ON binary mcs.charge_box_id = binary cn.charge_box_id INNER JOIN mdb_customer mcust ON binary mcust.id_tag = binary trx.id_tag INNER JOIN mdb_city mcity ON mcs.city = mcity.id WHERE mcust.ID =?1 ", nativeQuery = true)
	public Page<Map<String, Object>> findTransactionByUserId(String idTag, Pageable pageable);

//	@Query(value = "SELECT tsr.transaction_pk FROM transaction_start tsr INNER JOIN connector c ON tsr.connector_pk = c.connector_pk WHERE tsr.id_tag = ?1 AND c.charge_box_id = ?2 AND c.connector_id = ?3 AND tsr.transaction_pk NOT IN (SELECT tsp.transaction_pk FROM transaction_stop tsp) ", nativeQuery = true)

/*	@Query(value = "SELECT tsr.transaction_pk, c.charge_box_id, c.connector_id, tsr.start_timestamp, tsr.start_value "
			+ "FROM transaction_start tsr " + "INNER JOIN connector c ON tsr.connector_pk = c.connector_pk\r\n"
			+ "WHERE tsr.id_tag = ?1 AND tsr.transaction_pk NOT IN (SELECT tsp.transaction_pk FROM transaction_stop tsp)", nativeQuery = true)
	public List<Map<String, Object>> findRemoteStartTransactionId(String idTag);
*/

	//Udah join connector_custom
	@Query(value = "SELECT p.transaction_pk, p.charge_box_id, mcs.NAME AS nameCS, mcs.LOCATION AS address, "
			+"p.connector_id, "
			+"p.start_timestamp, p.start_value, mcs.longitude, mcs.latitude "
			+"FROM ( "
				+"SELECT tsr.transaction_pk, c.charge_box_id AS charge_box_id, "
			//	+"c.connector_id, "
				+"c.connector_custom AS connector_id, "
				+"tsr.start_timestamp, tsr.start_value "
				+"FROM transaction_start tsr  "
			//	+"INNER JOIN connector c ON tsr.connector_pk = c.connector_pk "
				+"INNER JOIN connector_custom c ON tsr.connector_pk = c.connector_pk "
				+"WHERE tsr.id_tag = ?1 "
				+"AND tsr.transaction_pk NOT IN (SELECT tsp.transaction_pk FROM transaction_stop tsp) "
				+") p "
				+"INNER JOIN mdb_cs mcs " 
				+"ON (p.charge_box_id collate UTF8_GENERAL_CI)= "
				+"(`mcs`.`CHARGE_BOX_ID` collate UTF8_GENERAL_CI)", nativeQuery = true)
	public List<Map<String, Object>> findRemoteStartTransactionId(String idTag);
}
