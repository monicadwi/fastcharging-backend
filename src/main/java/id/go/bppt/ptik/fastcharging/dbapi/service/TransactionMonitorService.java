package id.go.bppt.ptik.fastcharging.dbapi.service;

import java.util.List;

import id.go.bppt.ptik.fastcharging.dbapi.model.OutputWrapper;
import id.go.bppt.ptik.fastcharging.dbapi.model.TransactionMonitor;

public interface TransactionMonitorService {
	OutputWrapper findAllTransactionMonitor(int pageNo, int pageSize);
	
	List<TransactionMonitor> findTransactionMonitorByIdTag(String idTag, int pageNo, int pageSize);

	List<TransactionMonitor> findTransactionMonitorByTransactionPk(int transactionPk);
}
