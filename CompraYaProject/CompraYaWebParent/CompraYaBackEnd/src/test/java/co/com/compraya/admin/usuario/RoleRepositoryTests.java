package co.com.compraya.admin.usuario;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import co.com.compraya.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {

	@Autowired
	private RoleRepository repo;
	
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin", "gestiona todo");
		Role savedRole = repo.save(roleAdmin);
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateRestRoles() {
		Role roleVendedor = new Role("Vendedor", "gestiona precio, "
				+ "clientes, despachos, pedidos y reporte de ventas");
		
		Role roleEditor = new Role("Editor", "gestiona categorias, marcas"
				+ "productos, artículos y menús");
		
		Role roleDespachador = new Role("Despachador", "consulta productos, consulta pedidos, "
				+ "y actualiza el estado de pedidos");
		
		Role roleAsistente = new Role("Asistente", "gestiona preguntas y revisiones");

		repo.saveAll(List.of(roleVendedor, roleEditor, roleDespachador, roleAsistente));
	}
}
