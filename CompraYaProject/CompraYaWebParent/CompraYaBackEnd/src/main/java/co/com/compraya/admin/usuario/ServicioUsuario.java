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
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User getByEmail(String email) {
		return userRepo.getUsuarioByEmail(email);
	}
	
	public List<User> listAll() {
		return (List<User>) userRepo.findAll(Sort.by("primerNombre").ascending());
	}
	
	public Page<User> listByPage(int numeroPagina, String campoSort, String direccionSort,
			String textoBusqueda) {
		Sort sort = Sort.by(campoSort);
		
		sort = direccionSort.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(numeroPagina - 1, USUARIOS_POR_PAGINA, sort);
		
		if (textoBusqueda != null ) {
			return userRepo.findAll(textoBusqueda, pageable);
		}
		
		return userRepo.findAll(pageable);
	}

	public List<Role> listaRoles() {
		return (List<Role>) roleRepo.findAll();
		
	}
	
	public User guardar(User usuario) {
		boolean estaActualizandoUsuario = (usuario.getId() != null);
		
		if (estaActualizandoUsuario) {
			User usuarioExistente = userRepo.findById(usuario.getId()).get();
			if(usuario.getPassword().isEmpty()) {
				usuario.setPassword(usuarioExistente.getPassword());
			} else {
				encodePassword(usuario);
			}
		} else {
			encodePassword(usuario);
		}
	
		return userRepo.save(usuario);
	}
	
	public User actualizarCuenta (User usuarioEnForma) {
		User usuarioEnDb = userRepo.findById(usuarioEnForma.getId()).get();
		
		if (!usuarioEnForma.getPassword().isEmpty()) {
			usuarioEnDb.setPassword (usuarioEnForma.getPassword());
			encodePassword(usuarioEnDb);
		};
		
		if (usuarioEnForma.getFotos() != null) {
			usuarioEnDb.setFotos(usuarioEnForma.getFotos());
		}
		
		usuarioEnDb.setPrimerNombre(usuarioEnForma.getPrimerNombre());
		usuarioEnDb.setPrimerApellido(usuarioEnForma.getPrimerApellido());
		
		return userRepo.save(usuarioEnDb);
	}
	
	private	void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
	
	public boolean esEmailUnico(Integer id, String email) {
		User usuarioByEmail = userRepo.getUsuarioByEmail(email);
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
			return userRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException ("No pude encontrar algún usuario con ID " + id);
			
		}
	}
	
	public void eliminar(Integer id) throws UserNotFoundException {
		Long countById = userRepo.countById(id);
		if(countById == null || countById == 0) {
			throw new UserNotFoundException ("No pude encontrar algún usuario con ID " + id);
		}
		
		userRepo.deleteById(id);
	}
	
	public void updateEstadoUsuario (Integer id, boolean estadoActivo) {
		userRepo.updateEstadoUsuario (id, estadoActivo);
	}
}
