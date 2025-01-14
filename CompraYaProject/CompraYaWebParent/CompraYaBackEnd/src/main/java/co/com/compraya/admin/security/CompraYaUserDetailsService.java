package co.com.compraya.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import co.com.compraya.admin.usuario.UsuarioRepository;
import co.com.compraya.common.entity.Usuario;

public class CompraYaUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepo.getUsuarioByEmail(email);
		if (usuario != null) {
			// System.out.println(email);
			return new CompraYaUserDetails(usuario);
		}
		// System.out.println("Could not find user with email: " + email);
		throw new UsernameNotFoundException("Could not find user with email: " + email);
	}
}
