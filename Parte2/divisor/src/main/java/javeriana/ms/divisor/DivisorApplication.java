package javeriana.ms.divisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DivisorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DivisorApplication.class, args);
	}

}
