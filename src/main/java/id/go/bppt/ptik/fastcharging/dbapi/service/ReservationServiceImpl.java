package id.go.bppt.ptik.fastcharging.dbapi.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.go.bppt.ptik.fastcharging.dbapi.repository.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService{

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Override
	public List<Map<String, Object>> getReservationByIdTag(String idTag) {
		return reservationRepository.findReservationByReservationPkAvailable(idTag);
	}

}
