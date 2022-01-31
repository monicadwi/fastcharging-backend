package id.go.bppt.ptik.fastcharging.dbapi.service;

import java.util.List;
import java.util.Map;

public interface TransactionService {
	List<Map<String,Object>> findAllTransaction(int pageNo, int pageSize);
	List<Map<String,Object>> findTransactionByUserId(int pageNo, int pageSize, String idTag);
	List<Map<String, Object>> findRemoteStartTransactionId(String idTag);
}
