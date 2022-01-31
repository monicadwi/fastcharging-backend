package id.go.bppt.ptik.fastcharging.dbapi.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.go.bppt.ptik.fastcharging.dbapi.service.TransactionMonitorService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/backend/transactionmonitor")
@CrossOrigin
@Slf4j
public class TransactionMonitorController {

	@Autowired
	private TransactionMonitorService transactionMonitorService;

	@GetMapping("/byIdtag/{idTag}/{pageNo}/{pageSize}")
	public ResponseEntity<?> allTransactionsByUserIdTag(@PathVariable String idTag, @PathVariable int pageNo,
			@PathVariable int pageSize) {
		try {
			HashMap<String, Object> mapSuccess = new HashMap<>();

			mapSuccess.put("status", "success");
			mapSuccess.put("data", transactionMonitorService.findTransactionMonitorByIdTag(idTag, pageNo, pageSize));
			mapSuccess.put("message", "List transaksi complete ");

			return new ResponseEntity<Object>(mapSuccess, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("enter exception");
			HashMap<String, String> mapError = new HashMap<>();
			mapError.put("status", "error");
			mapError.put("message", ex.getMessage());

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapError);
		}
	}

	@GetMapping("/all/{pageNo}/{pageSize}")
	public ResponseEntity<?> allTransactions(@PathVariable int pageNo, @PathVariable int pageSize) {
		try {
			HashMap<String, Object> mapSuccess = new HashMap<>();

			mapSuccess.put("status", "success");
			mapSuccess.put("data", transactionMonitorService.findAllTransactionMonitor(pageNo, pageSize));
			mapSuccess.put("message", "List transaksi complete ");

			return new ResponseEntity<Object>(mapSuccess, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("enter exception");
			HashMap<String, String> mapError = new HashMap<>();
			mapError.put("status", "error");
			mapError.put("message", ex.getMessage());

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapError);
		}
	}

	@GetMapping("/byTransactionPk/{transactionPk}")
	public ResponseEntity<?> allTransactionsByTransactionPk(@PathVariable int transactionPk) {
		try {
			HashMap<String, Object> mapSuccess = new HashMap<>();

			mapSuccess.put("status", "success");
			mapSuccess.put("data", transactionMonitorService.findTransactionMonitorByTransactionPk(transactionPk));
			mapSuccess.put("message", "List transaksi complete ");

			return new ResponseEntity<Object>(mapSuccess, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("enter exception");
			HashMap<String, String> mapError = new HashMap<>();
			mapError.put("status", "error");
			mapError.put("message", ex.getMessage());

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapError);
		}
	}
}
