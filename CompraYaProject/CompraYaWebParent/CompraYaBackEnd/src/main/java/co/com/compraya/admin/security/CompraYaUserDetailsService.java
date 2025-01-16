package co.com.compraya.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import co.com.compraya.admin.usuario.UserRepository;
import co.com.compraya.common.entity.User;

public class CompraYaUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository usuarioRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User usuario = usuarioRepo.getUsuarioByEmail(email);
		if (usuario != null) {
			// System.out.println(email);
			return new CompraYaUserDetails(usuario);
		}
		// System.out.println("Could not find user with email: " + email);
		throw new UsernameNotFoundException("No se pudo encontrar un Usuario con email: " + email);
	}
}
