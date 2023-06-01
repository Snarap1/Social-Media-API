package sprng.boot.socialmediaapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import sprng.boot.socialmediaapi.auth.AuthenticationService;
import sprng.boot.socialmediaapi.auth.RegisterRequest;

import static sprng.boot.socialmediaapi.models.Role.USER;

@SpringBootApplication
@EnableWebMvc
public class socialmediaApplication {

	//  docker exec -it 209edc1dbdc6 psql -U postgres smartcart_db
	// http://localhost:8080/swagger-ui/index.html#/


	public static void main(String[] args) {
		SpringApplication.run(socialmediaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.username("SUPERUSER")
					.email("super@mail.com")
					.password("password")
					.role(USER)
					.build();
			System.out.println("SuperUser token: " + service.register(admin).getAccessToken());

		};
	}


}

