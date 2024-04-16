package com.pweb.gourmetguide;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
	@Info(title = "GourmetGuide", version = "1.0", description = "GourmetGuide SpringBoot Application")
)
public class GourmetGuideApplication {

	public static void main(String[] args) {
		SpringApplication.run(GourmetGuideApplication.class, args);
	}

}
