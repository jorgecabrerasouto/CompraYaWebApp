package co.com.compraya.admin.categoria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import co.com.compraya.common.entity.Categoria;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class CategoriaRepositoryTests {

	@Autowired
	private CategoriaRepository repo;
	
	@Test
	public void testCreateRootCategory() {
		Categoria categoria = new Categoria("Electrónicos");
		Categoria savedCategoria = repo.save(categoria);
		
		assertThat(savedCategoria.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateSubCategory() {
		Categoria padre = new Categoria(7);
		Categoria subCategoria = new Categoria("Iphone", padre);
		Categoria savedCategoria = repo.save(subCategoria);
		
		assertThat(savedCategoria.getId()).isGreaterThan(0);

	}
	
	@Test
	public void testGetCategoria() {
		Categoria categoria = repo.findById(2).get();
		System.out.println(categoria.getNombre());
		
		Set<Categoria> hijos = categoria.getHijos();
		for (Categoria subCategoria : hijos) {
			System.out.println(subCategoria.getNombre());
		}
		
		assertThat(hijos.size()).isGreaterThan(0);
	}
	
	@Test
	public void testPrintCategoriasJerarquicamente () {
		Iterable<Categoria> categorias = repo.findAll();
		
		for (Categoria categoria : categorias) {
			if (categoria.getPadre() == null) {
				System.out.println(categoria.getNombre());
				
				Set<Categoria> hijo = categoria.getHijos(); 
				
				for (Categoria subCategoria : hijo) {
					System.out.println("--" + subCategoria.getNombre());
					printHijos(subCategoria, 1);
				}
			}
		}
	}
	private void printHijos (Categoria padre, int subNivel) {
		int nuevoSubNivel = ++subNivel;
		Set<Categoria> hijos = padre.getHijos();  

		for	(Categoria subCategoria : hijos) {
			for(int i = 0; i < nuevoSubNivel; i++) {
				System.out.print("--");
			}
			System.out.println(subCategoria.getNombre());
			
			printHijos(subCategoria, nuevoSubNivel);
		}
	}
	
	@Test
	public void testListaCategoriasRaiz() {
		List<Categoria> categoriasRaiz = repo.encuentraCategoriasRaiz();
		categoriasRaiz.forEach(cat -> System.out.println(cat.getNombre()));
	}
}
