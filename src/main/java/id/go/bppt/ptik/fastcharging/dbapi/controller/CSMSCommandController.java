package id.go.bppt.ptik.fastcharging.dbapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/backend/csms")
@CrossOrigin
public class CSMSCommandController {
	@Autowired
	RestTemplate restTemplate;

	@Value("${steve.baseurl}")
	String csmsUrl;

	@RequestMapping(value = "/ReserveNow", method = RequestMethod.POST)
	public ResponseEntity<?> reserveNow(@RequestBody MultiValueMap<String, Object> reserveNowFormData) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<MultiValueMap<String, Object>> postEntity = new HttpEntity<>(reserveNowFormData, headers);

		ResponseEntity<String> response = restTemplate.exchange(csmsUrl + "ReserveNowTransaction", HttpMethod.POST,
				postEntity, String.class);

		return response;
	}

	@RequestMapping(value = "/CancelReservation", method = RequestMethod.POST)
	public ResponseEntity<?> cancelReservation(@RequestBody MultiValueMap<String, Object> cancelReservationFormData) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<MultiValueMap<String, Object>> postEntity = new HttpEntity<>(cancelReservationFormData, headers);

		ResponseEntity<String> response = restTemplate.exchange(csmsUrl + "CancelReservationTransaction",
				HttpMethod.POST, postEntity, String.class);

		return response;
	}
}
