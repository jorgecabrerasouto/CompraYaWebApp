package co.com.compraya.admin.categoria;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.compraya.common.entity.Categoria;

@Service
public class ServicioCategoria {

	@Autowired
	private CategoriaRepository repo;
	
	public List<Categoria> listAll() {
		List<Categoria> categoriasRaiz = repo.encuentraCategoriasRaiz();
		return ListaCategoriasJerarquicamente(categoriasRaiz);
	}
	
	private List<Categoria> ListaCategoriasJerarquicamente(List<Categoria> categoriasRaiz) {
		List<Categoria> categoriasJerarquicamente = new ArrayList<>();
		
		for (Categoria categoriaRaiz : categoriasRaiz) {
			categoriasJerarquicamente.add(Categoria.copiaCompleta(categoriaRaiz));
			
			Set<Categoria> hijo = categoriaRaiz.getHijos();
			
			for (Categoria subCategoria : hijo) {
				String nombre = "--" + subCategoria.getNombre();
				categoriasJerarquicamente.add(Categoria.copiaCompleta(subCategoria, nombre));
				
				ListaSubCategoriasJerarquicamente(categoriasJerarquicamente, subCategoria, 1);
			}
		}
		
		return categoriasJerarquicamente;
	}
	
	public void ListaSubCategoriasJerarquicamente(List<Categoria> categoriasJerarquicamente,
			Categoria padre, int subNivel) {
		int nuevoSubNivel = ++subNivel;
		Set<Categoria> hijo = padre.getHijos();
		for (Categoria subCategoria : hijo ) {
			String nombre = "";
			for(int i = 0; i < nuevoSubNivel; i++) {
				nombre += "--";
			}
			
			nombre += subCategoria.getNombre();
			
			categoriasJerarquicamente.add(Categoria.copiaCompleta(subCategoria, nombre));
			
			ListaSubCategoriasJerarquicamente(categoriasJerarquicamente, subCategoria, nuevoSubNivel);
		}
	}
	
	public Categoria save(Categoria categoria) {
		return repo.save(categoria);
	}
	
	public List<Categoria> listaCategoriasUsadaEnForma() {
		List<Categoria> categoriasUsadasEnForma = new ArrayList<>();
		
		Iterable<Categoria> categoriasEnBD = repo.findAll();
		
		for (Categoria categoria : categoriasEnBD) {
			if (categoria.getPadre() == null) {
				categoriasUsadasEnForma.add(Categoria.copieIdYNombre(categoria));
				
				Set<Categoria> hijo = categoria.getHijos(); 
				
				for (Categoria subCategoria : hijo) {
					String nombre = "--" + subCategoria.getNombre();
					categoriasUsadasEnForma.add(Categoria.copieIdYNombre(subCategoria.getId(), nombre));
					ListarSubCategoriasUsadasEnForma (categoriasUsadasEnForma, subCategoria, 1);
				}
			}
		}
		
		return categoriasUsadasEnForma;
	}
	
	private void ListarSubCategoriasUsadasEnForma (List<Categoria> categoriasUsadasEnForma, Categoria padre, int subNivel) {
		int nuevoSubNivel = ++subNivel;
		Set<Categoria> hijos = padre.getHijos();  

		for	(Categoria subCategoria : hijos) {
			String nombre = "";
			for(int i = 0; i < nuevoSubNivel; i++) {
				nombre += "--";
			}
			
			nombre += subCategoria.getNombre();
			
			categoriasUsadasEnForma.add(Categoria.copieIdYNombre(subCategoria.getId(), nombre));
			
			ListarSubCategoriasUsadasEnForma(categoriasUsadasEnForma, subCategoria, nuevoSubNivel);
		}
	}
	
	public Categoria get(Integer id) throws CategoriaNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new CategoriaNotFoundException("No se pudo encontrar una categoria con ID " +id);
		}
	}
	
	public String chequearUnico(Integer id, String nombre, String alias) {
		boolean isCreatingNew = (id == null || id == 0);
		
		Categoria categoriaByNombre = repo.findByNombre(nombre);
		
		if (isCreatingNew) {
			if (categoriaByNombre != null) {
				return "NombreDuplicado";
			}
			else {
				Categoria categoriaByAlias = repo.findByAlias(alias);
				if (categoriaByAlias != null) {
					return "AliasDuplicado";
				}
			}
		} else {
			if(categoriaByNombre != null && categoriaByNombre.getId() != id) {
				return "NombreDuplicado";
			}
			Categoria categoriaByAlias =repo.findByAlias(alias);
			if (categoriaByAlias != null && categoriaByAlias.getId() != id) {
				return "AliasDuplicado";
			}
		}
		return "OK";
	}
}
