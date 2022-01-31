package id.go.bppt.ptik.fastcharging.dbapi;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.google.maps.GeoApiContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)   
	{  
		return application.sources(Application.class);  
	}  

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Value("${com.google.api.maps.key}")
	String mapsApiKey;

	@Bean
	public GeoApiContext myContext() {
		return new GeoApiContext.Builder().apiKey(mapsApiKey).build();
	}
	
	@PreDestroy
	public void destroyGeoContext() {
		myContext().shutdown();
	}
}
