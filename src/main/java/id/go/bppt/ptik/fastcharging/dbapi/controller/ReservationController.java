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

import id.go.bppt.ptik.fastcharging.dbapi.service.ReservationService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/backend/reservation")
@CrossOrigin
@Slf4j
public class ReservationController {
	@Autowired
    private ReservationService reservationService;
	
	@GetMapping("/current/{idTag}")
	public ResponseEntity<?> getMyReservation(@PathVariable String idTag){
        try {
            HashMap<String, Object> mapSuccess = new HashMap<>();
		    mapSuccess.put("status", "success");
		    mapSuccess.put("data", reservationService.getReservationByIdTag(idTag));
		    mapSuccess.put("message", "List reservasi complete");

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
