package co.com.compraya.admin;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
@EntityScan({"co.com.compraya.common.entity", "co.com.compraya.admin.usuario"})
public class CompraYaBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompraYaBackEndApplication.class, args);
	}

    @Bean
    public MessageSource messageSource() {
        Locale.setDefault(Locale.of("es", "CO"));
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.addBasenames("classpath:org/springframework/security/messages");
        return messageSource;
    }
}
