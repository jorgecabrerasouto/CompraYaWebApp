package co.com.compraya.admin.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.com.compraya.common.entity.Role;
import co.com.compraya.common.entity.Usuario;

@Service
public class ServicioUsuario {
	
	@Autowired
	private UsuarioRepository usuarioRepo;

	@Autowired
	private RoleRepository roleRepo;	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<Usuario> listAll() {
		return (List<Usuario>) usuarioRepo.findAll();
	}

	public List<Role> listaRoles() {
		return (List<Role>) roleRepo.findAll();
		
	}
	
	public void guardar(Usuario usuario) {
		encodePassword(usuario);
		usuarioRepo.save(usuario);
	}
	
	private	void encodePassword(Usuario usuario) {
		String encodedPassword = passwordEncoder.encode(usuario.getPassword());
		usuario.setPassword(encodedPassword);
	}
}
