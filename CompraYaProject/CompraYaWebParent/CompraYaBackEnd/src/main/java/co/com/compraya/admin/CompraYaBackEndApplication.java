package co.com.compraya.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"co.com.compraya.common.entity", "co.com.compraya.admin.usuario"})
public class CompraYaBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompraYaBackEndApplication.class, args);
	}

}
