package co.com.compraya.admin.producto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import co.com.compraya.common.entity.Marca;
import co.com.compraya.admin.producto.ProductoRepository;
import co.com.compraya.common.entity.Categoria;
import co.com.compraya.common.entity.Producto;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductoRepositoryTest {

	@Autowired
	private ProductoRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCrearProducto() {
	
//		Marca marca = entityManager.find(Marca.class, 10);
//		Categoria categoria = entityManager.find(Categoria.class, 15);
		
//		Marca marca = entityManager.find(Marca.class, 38);
//		Categoria categoria = entityManager.find(Categoria.class, 6);
		
		Marca marca = entityManager.find(Marca.class, 37);
		Categoria categoria = entityManager.find(Categoria.class, 5);
		
		Producto producto = new Producto();
		
//		producto.setNombre("Samsung Galaxy A31");
//		producto.setAlias("samsung_galaxy_a31");
//		producto.setDescripcionCorta("Un buen teléfono inteligente de Samsung");
//		producto.setDescripcionCompleta("Descripión completa para un muy buen teléfono inteligente");		
		
//		producto.setNombre("Dell Inspiron 3000");
//		producto.setAlias("dell_inspiron_3000");
//		producto.setDescripcionCorta("Descripión corta para Dell Inspiron 3000");
//		producto.setDescripcionCompleta("Descripión completa para Dell Inspiron 3000");
		
		producto.setNombre("Acer Aspire Desktop");
		producto.setAlias("acer_aspire_desktop");
		producto.setDescripcionCorta("Descripión corta para Acer Aspire");
		producto.setDescripcionCompleta("Descripión completa para Acer Aspire");		
		
		producto.setMarca(marca);
		producto.setCategoria(categoria);
		
		producto.setPrecio(678);
		producto.setCosto(600);
		producto.setActivo(true);
		producto.setEnExistencia(true);
		
		producto.setFechaCreacion(new Date());
		producto.setFechaActualizacion(new Date());
		
		Producto productoGuardado = repo.save(producto);
		
		assertThat(productoGuardado).isNotNull();
		assertThat(productoGuardado.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllProductos() {
		Iterable<Producto> iterableProducts = repo.findAll();
		
		iterableProducts.forEach(System.out::println);
	}
	
	@Test
	public void testGetProducto() {
		Integer id = 2;
		Producto producto = repo.findById(id).get();
		System.out.println(producto);
		
		assertThat(producto).isNotNull();
	}
	
	@Test
	public void testActualizarProducto() {
		Integer id = 1;
		Producto producto = repo.findById(id).get();
		producto.setPrecio(499);
		
		repo.save(producto);
		
		Producto productoActualizado = entityManager.find(Producto.class, id);
		
		assertThat(productoActualizado.getPrecio()).isEqualTo(499);
	}
	
	@Test
	public void testEliminarProducto() {
		Integer id = 3;
		repo.deleteById(id);
		
		Optional<Producto> result = repo.findById(id);
		
		assertThat(!result.isPresent());
	}
}
