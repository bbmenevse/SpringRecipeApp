package guru.springframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "guru.springframework.repositories")
public class 	Spring5RecipeAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring5RecipeAppApplication.class, args);
	}
}
