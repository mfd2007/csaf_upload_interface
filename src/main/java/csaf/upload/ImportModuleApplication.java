package csaf.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImportModuleApplication {

	public static final String BASE_ROUTE = "/api/v1/";
	
	public static void main(String[] args) {
		SpringApplication.run(ImportModuleApplication.class, args);
	}

}
