package co.com.compraya.admin.marca;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.com.compraya.common.entity.Marca;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ServicioMarcaTest {

	@SuppressWarnings("removal")
	@MockBean private MarcaRepository repository;
	
	@InjectMocks
	private ServicioMarca servicio;
	
	@Test
	public void testCheckUniqueInNewModeReturnNombreDuplicada() {
		Integer id = null;
		String nombre = "Acer";;
		Marca marca = new Marca(nombre);
		
		Mockito.when(repository.findByNombre(nombre)).thenReturn(marca);
		
		String resultado=servicio.chequearUnico(id, nombre);
		
		assertThat(resultado).isEqualTo("Duplicada");
	}
	
	@Test
	public void testCheckUniqueInNewModeReturnOK() {
		Integer id = null;
		String nombre = "Amd";;
		
		Mockito.when(repository.findByNombre(nombre)).thenReturn(null);
		
		String resultado=servicio.chequearUnico(id, nombre);
		
		assertThat(resultado).isEqualTo("OK");
	}
	
	@Test
	public void testCheckUniqueInEditModeReturnDuplicada() {
		Integer id = 1;
		String nombre = "Canon";
		Marca marca = new Marca(id, nombre);

		Mockito.when(repository.findByNombre(nombre)).thenReturn(marca);
		
		String resultado=servicio.chequearUnico(2, "Canon");
		
		assertThat(resultado).isEqualTo("Duplicada");
	}

	@Test
	public void testCheckUniqueInEditModeReturnOK() {
		Integer id = 1;
		String nombre = "Acer";
		
		Marca marca = new Marca(id, nombre);

		Mockito.when(repository.findByNombre(nombre)).thenReturn(marca);
		
		String resultado=servicio.chequearUnico(id, nombre);
		
		assertThat(resultado).isEqualTo("OK");
	}	
}
