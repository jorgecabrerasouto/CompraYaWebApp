package co.com.compraya.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		return new CompraYaUserDetailsService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	@Bean
	SecurityFilterChain configureHttp(HttpSecurity http) throws Exception {
		http.authenticationProvider(authenticationProvider());
		
		http.authorizeHttpRequests(auth -> auth
				.anyRequest().authenticated()
			)
			.formLogin(form -> form			
				.loginPage("/login")
				.usernameParameter("email")
				.permitAll())

			.logout(logout -> logout.permitAll())
			
			.rememberMe ((remember) -> remember
					.key("AbcDefgHijklmnOpqrs_1234597890")
					.tokenValiditySeconds(7 * 24 * 60 * 60));
			
		return http.build();
	}
	
	@Bean
	WebSecurityCustomizer configureWebSecurity() throws Exception {
		return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
	}
	
}
