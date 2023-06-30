package lycoris62.socialLoginApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SocialLoginAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialLoginAppApplication.class, args);
	}

}
