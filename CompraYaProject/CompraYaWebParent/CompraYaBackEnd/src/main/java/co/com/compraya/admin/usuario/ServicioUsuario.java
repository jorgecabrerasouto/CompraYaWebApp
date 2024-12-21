package co.com.compraya.admin.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.compraya.common.entity.Role;
import co.com.compraya.common.entity.Usuario;

@Service
public class ServicioUsuario {
	
	@Autowired
	private UsuarioRepository usuarioRepo;

	@Autowired
	private RoleRepository roleRepo;	
	
	public List<Usuario> listAll() {
		return (List<Usuario>) usuarioRepo.findAll();
	}

	public List<Role> listaRoles() {
		return (List<Role>) roleRepo.findAll();
		
	}
	
	public void guardar(Usuario usuario) {
		usuarioRepo.save(usuario);
	}
}
