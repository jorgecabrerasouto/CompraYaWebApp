package co.com.compraya.admin.marca;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import co.com.compraya.admin.marca.MarcaRepository;
import co.com.compraya.common.entity.Categoria;
import co.com.compraya.common.entity.Marca;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)

public class MarcaRepositoryTest {
	
	@Autowired
	private MarcaRepository repo;
	
	@Test
	public void testCreateMarca1() {
		Categoria laptops = new Categoria(6);
		Marca acer = new Marca("Acer");
		acer.getCategorias().add(laptops);
		
		Marca marcaGuardada = repo.save(acer);
		assertThat(marcaGuardada).isNotNull();
		assertThat(marcaGuardada.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateMarca2() {
		Categoria cellphones = new Categoria(4);
		Categoria tablets = new Categoria(7);
		Marca apple = new Marca("Apple");
		apple.getCategorias().add(cellphones);
		apple.getCategorias().add(tablets);
		
		Marca marcaGuardada = repo.save(apple);
		assertThat(marcaGuardada).isNotNull();
		assertThat(marcaGuardada.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateMarca3() {
		Marca samsung = new Marca("Samsung");
		samsung.getCategorias().add(new Categoria(29));		// categoria memoria
		samsung.getCategorias().add(new Categoria(24));		// categoria internal hard drive
		
		Marca marcaGuardada = repo.save(samsung);
		assertThat(marcaGuardada).isNotNull();
		assertThat(marcaGuardada.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testFindAll() {
		Iterable<Marca> marcas = repo.findAll();
		marcas.forEach(System.out::println);
		
		assertThat(marcas).isNotEmpty();
	}
	
	@Test
	public void testGetById() {
		Marca marca = repo.findById(1).get();
		
		assertThat(marca.getNombre()).isEqualTo("Acer");
	}
	
	@Test
	public void testUpdateNombre() {
		String nuevoNombre = "Samsung Electronics";
		Marca samsung = repo.findById(3).get();
		samsung.setNombre(nuevoNombre);
		
		Marca marcaGuardada = repo.save(samsung);
		assertThat(marcaGuardada.getNombre()).isEqualTo(nuevoNombre);
	}
	
	@Test
	public void testDelete() {
		Integer id = 2;
		repo.deleteById(id);
		
		Optional<Marca> result = repo.findById(id);
		
		assertThat(result.isEmpty());
	}	
}
