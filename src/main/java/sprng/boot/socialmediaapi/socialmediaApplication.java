package sprng.boot.socialmediaapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class socialmediaApplication {

	//  docker exec -it 209edc1dbdc6 psql -U postgres smartcart_db
	// http://localhost:8080/swagger-ui/index.html#/


	public static void main(String[] args) {
		SpringApplication.run(socialmediaApplication.class, args);
	}


}

