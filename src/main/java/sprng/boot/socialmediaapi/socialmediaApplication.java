package sprng.boot.socialmediaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
