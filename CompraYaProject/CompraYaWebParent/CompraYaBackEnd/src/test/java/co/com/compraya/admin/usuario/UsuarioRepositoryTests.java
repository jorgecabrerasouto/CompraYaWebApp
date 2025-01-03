package co.com.compraya.admin.usuario;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import co.com.compraya.common.entity.Role;
import co.com.compraya.common.entity.Usuario;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class UsuarioRepositoryTests {
	
	@Autowired
	private UsuarioRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCrearUsuarioConUnRol() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		Usuario usuarioJorge = new Usuario("jorgecabrerasouto@gmail.com", "jorge2024", "Jorge", "Cabrera" );
		usuarioJorge.addRole(roleAdmin);
		
		Usuario usuarioGuardado =repo.save(usuarioJorge);
		
		assertThat(usuarioGuardado.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCrearUsuarioConDosRoles() {
		Usuario usuarioJuan = new Usuario("juan1234@gmail.com", "juan2024", "Juan", "Perez" );
		Role roleEditor = new Role(3);
		Role roleAsistente = new Role(5);
		
		usuarioJuan.addRole(roleEditor);
		usuarioJuan.addRole(roleAsistente);
		
		Usuario usuarioGuardado =repo.save(usuarioJuan);
		
		assertThat(usuarioGuardado.getId()).isGreaterThan(0);
	}	
	
	@Test
	public void testListTodosLosUsuarios() {
		Iterable<Usuario> listUsuarios = repo.findAll();
		listUsuarios.forEach(usuario -> System.out.println(usuario));
	}

	@Test
	public void testGetUsuarioById() {
		Usuario usuarioJorge = repo.findById(1).get();
		System.out.println(usuarioJorge);
		assertThat(usuarioJorge).isNotNull();
	}
	
	@Test
	public void testUsdateDetalleUsuario() {
		Usuario usuarioJorge = repo.findById(1).get();
		usuarioJorge.setActivo(true);
		usuarioJorge.setEmail("jorgecabrerasouto@outlook.com");
		
		repo.save(usuarioJorge);	
	}
	@Test
	public void testUpdateRolesUsuario() {
		Usuario usuarioJuan = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleVendedor = new Role(2);
		
		usuarioJuan.getRoles().remove(roleEditor);
		usuarioJuan.addRole(roleVendedor);
		
		repo.save(usuarioJuan);
	}
	
	@Test
	public void testDeleteUsuario() {
		Integer userId = 2;
		repo.deleteById(userId);
	}
	
	@Test
	public void testGetUsuarioByEmail() {
		String email = "jorgecabrerasouto@outlook.com";
		Usuario usuario = repo.getUsuarioByEmail(email);
		
		assertThat(usuario).isNotNull();
		
	}
	
	@Test
	public void testCountById () {
		Integer id = 1;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}

	@Test
	public void testDesactivarUsuario() {
		Integer id = 1;
		repo.updateEstadoUsuario(id, false);
	}
	
	@Test
	public void testActivarUsuario() {
		Integer id = 3;
		repo.updateEstadoUsuario(id, true);
	}
	
	@Test
	public void testListFirstPage() {
		int numeroPagina = 0;
		int tamanoPagina = 4;
		
		Pageable pageable = PageRequest.of(numeroPagina,  tamanoPagina);
		Page<Usuario> pagina = repo.findAll(pageable);
		
		List<Usuario> listaUsuarios = pagina.getContent();
	
		listaUsuarios.forEach(usuario -> System.out.println(usuario));
		assertThat(listaUsuarios.size()).isEqualTo(tamanoPagina);
	}
}
