package co.com.compraya.admin.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import co.com.compraya.common.entity.Role;
import co.com.compraya.common.entity.User;

public class CompraYaUserDetails implements UserDetails {
	
	private User usuario;

	public CompraYaUserDetails(User usuario) {
		this.usuario = usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles = usuario.getRoles();
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getNombre()));
		}
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return usuario.getPassword();
	}

	@Override
	public String getUsername() {
		return usuario.getEmail();
	}

	@Override
	public boolean isEnabled() {
		return usuario.isEnabled();
	}

	public String getFullname() {
		return this.usuario.getPrimerNombre() + " " + this.usuario.getPrimerApellido();
	}
}
