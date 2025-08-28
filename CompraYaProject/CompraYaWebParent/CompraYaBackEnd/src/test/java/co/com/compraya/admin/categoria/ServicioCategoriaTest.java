package co.com.compraya.admin.categoria;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.nullable;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.com.compraya.common.entity.Categoria;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ServicioCategoriaTest {

	@SuppressWarnings("removal")
	@MockBean
	private CategoriaRepository repository;
	
	@InjectMocks
	private ServicioCategoria servicio;
	
	@Test
	public void testCheckUniqueInNewModeReturnNombreDuplicado() {
		Integer id = null;
		String nombre = "Computadores";
		String alias = "abc";
		
		Categoria categoria = new Categoria(id, nombre, alias);
		
		Mockito.when(repository.findByNombre(nombre)).thenReturn(categoria);
		Mockito.when(repository.findByAlias(alias)).thenReturn(null);
		
		String resultado=servicio.chequearUnico(id, nombre, alias);
		
		assertThat(resultado).isEqualTo("NombreDuplicado");
	}
	
	@Test
	public void testCheckUniqueInNewModeReturnAliasDuplicado() {
		Integer id = null;
		String nombre = "NombreABC";
		String alias = "computadores";
		
		Categoria categoria = new Categoria(id, nombre, alias);
		
		Mockito.when(repository.findByNombre(nombre)).thenReturn(null);
		Mockito.when(repository.findByAlias(alias)).thenReturn(categoria);
		
		String resultado=servicio.chequearUnico(id, nombre, alias);
		
		assertThat(resultado).isEqualTo("AliasDuplicado");
	}
	
	@Test
	public void testCheckUniqueInNewModeReturnOK() {
		Integer id = null;
		String nombre = "Computadores";
		String alias = "computadores";

		Mockito.when(repository.findByNombre(nombre)).thenReturn(null);
		Mockito.when(repository.findByAlias(alias)).thenReturn(null);
		
		String resultado=servicio.chequearUnico(id, nombre, alias);
		
		assertThat(resultado).isEqualTo("OK");
	}
	
	@Test
	public void testCheckUniqueInEditModeReturnNombreDuplicado() {
		Integer id = 1;
		String nombre = "Computadores";
		String alias = "abc";
		
		Categoria categoria = new Categoria(2, nombre, alias);
		
		Mockito.when(repository.findByNombre(nombre)).thenReturn(categoria);
		Mockito.when(repository.findByAlias(alias)).thenReturn(null);
		
		String resultado=servicio.chequearUnico(id, nombre, alias);
		
		assertThat(resultado).isEqualTo("NombreDuplicado");
	}
	
	@Test
	public void testCheckUniqueInEditModeReturnAliasDuplicado() {
		Integer id = 1;
		String nombre = "NombreABC";
		String alias = "computadores";
		
		Categoria categoria = new Categoria(2, nombre, alias);
		
		Mockito.when(repository.findByNombre(nombre)).thenReturn(null);
		Mockito.when(repository.findByAlias(alias)).thenReturn(categoria);
		
		String resultado=servicio.chequearUnico(id, nombre, alias);
		
		assertThat(resultado).isEqualTo("AliasDuplicado");
	}
	
	@Test
	public void testCheckUniqueInEditModeReturnOK() {
		Integer id = 1;
		String nombre = "Computadores";
		String alias = "computadores";
		
		Categoria categoria = new Categoria(id, nombre, alias);

		Mockito.when(repository.findByNombre(nombre)).thenReturn(null);
		Mockito.when(repository.findByAlias(alias)).thenReturn(categoria);
		
		String resultado=servicio.chequearUnico(id, nombre, alias);
		
		assertThat(resultado).isEqualTo("OK");
	}	
}
