package co.com.compraya.admin.usuario;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.com.compraya.common.entity.Role;
import co.com.compraya.common.entity.User;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ServicioUsuario {
	public static final int USUARIOS_POR_PAGINA = 6;
	
	@Autowired
	private UserRepository usuarioRepo;

	@Autowired
	private RoleRepository roleRepo;	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> listAll() {
		return (List<User>) usuarioRepo.findAll(Sort.by("primerNombre").ascending());
	}
	
	public Page<User> listByPage(int numeroPagina, String campoSort, String direccionSort,
			String textoBusqueda) {
		Sort sort = Sort.by(campoSort);
		
		sort = direccionSort.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(numeroPagina - 1, USUARIOS_POR_PAGINA, sort);
		
		if (textoBusqueda != null ) {
			return usuarioRepo.findAll(textoBusqueda, pageable);
		}
		
		return usuarioRepo.findAll(pageable);
	}

	public List<Role> listaRoles() {
		return (List<Role>) roleRepo.findAll();
		
	}
	
	public User guardar(User usuario) {
		boolean estaActualizandoUsuario = (usuario.getId() != null);
		
		if (estaActualizandoUsuario) {
			User usuarioExistente = usuarioRepo.findById(usuario.getId()).get();
			if(usuario.getPassword().isEmpty()) {
				usuario.setPassword(usuarioExistente.getPassword());
			} else {
				encodePassword(usuario);
			}
		} else {
			encodePassword(usuario);
		}
	
		return usuarioRepo.save(usuario);
	}
	
	private	void encodePassword(User usuario) {
		String encodedPassword = passwordEncoder.encode(usuario.getPassword());
		usuario.setPassword(encodedPassword);
	}
	
	public boolean esEmailUnico(Integer id, String email) {
		User usuarioByEmail = usuarioRepo.getUsuarioByEmail(email);
		if (usuarioByEmail == null) return true;
		
		boolean estaCreandoNuevo = (id == null);
		
		if(estaCreandoNuevo) {
			if(usuarioByEmail != null) return false;
		} else {
			if(usuarioByEmail.getId() != id) {
				return false;
			}
		}
		
		return true;
	}

	public User get(Integer id) throws UserNotFoundException {
		try {
			return usuarioRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException ("No pude encontrar algún usuario con ID " + id);
			
		}
	}
	
	public void eliminar(Integer id) throws UserNotFoundException {
		Long countById = usuarioRepo.countById(id);
		if(countById == null || countById == 0) {
			throw new UserNotFoundException ("No pude encontrar algún usuario con ID " + id);
		}
		
		usuarioRepo.deleteById(id);
	}
	
	public void updateEstadoUsuario (Integer id, boolean estadoActivo) {
		usuarioRepo.updateEstadoUsuario (id, estadoActivo);
	}
}
