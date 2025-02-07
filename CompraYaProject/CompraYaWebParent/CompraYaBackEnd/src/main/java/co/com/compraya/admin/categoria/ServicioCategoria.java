package co.com.compraya.admin.categoria;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.compraya.common.entity.Categoria;

@Service
public class ServicioCategoria {

	@Autowired
	private CategoriaRepository repo;
	
	public List<Categoria> listAll() {
		return (List<Categoria>) repo.findAll();
	}
	
	public List<Categoria> listaCategoriasUsadaEnForma() {
		List<Categoria> categoriasUsadasEnForma = new ArrayList<>();
		
		Iterable<Categoria> categoriasEnBD = repo.findAll();
		
		for (Categoria categoria : categoriasEnBD) {
			if (categoria.getPadre() == null) {
				categoriasUsadasEnForma.add(new Categoria(categoria.getNombre()));
				
				Set<Categoria> hijo = categoria.getHijos(); 
				
				for (Categoria subCategoria : hijo) {
					String nombre = "--" + subCategoria.getNombre();
					categoriasUsadasEnForma.add(new Categoria(nombre));
					ListarHijos(categoriasUsadasEnForma, subCategoria, 1);
				}
			}
		}
		
		return categoriasUsadasEnForma;
	}
	
	private void ListarHijos (List<Categoria> categoriasUsadasEnForma, Categoria padre, int subNivel) {
		int nuevoSubNivel = ++subNivel;
		Set<Categoria> hijos = padre.getHijos();  

		for	(Categoria subCategoria : hijos) {
			String nombre = "";
			for(int i = 0; i < nuevoSubNivel; i++) {
				nombre += "--";
			}
			
			nombre += subCategoria.getNombre();
			
			categoriasUsadasEnForma.add(new Categoria(nombre));
			
			ListarHijos(categoriasUsadasEnForma, subCategoria, nuevoSubNivel);
		}
	}	
}
