package co.com.compraya.admin.categoria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import co.com.compraya.common.entity.Categoria;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ServicioCategoria {
	@Autowired
	private CategoriaRepository repo;
	
	public List<Categoria> listAll(String sortDir) {
		Sort sort = Sort.by("nombre");
		
		if (sortDir.equals("asc")) {
			sort = sort.ascending();
		} else if (sortDir.equals("desc")) {
			sort =sort.descending();
		}
		
		List<Categoria> categoriasRaiz = repo.encuentraCategoriasRaiz(sort);
		return ListaCategoriasJerarquicamente(categoriasRaiz, sortDir);
	}
	
	private List<Categoria> ListaCategoriasJerarquicamente(List<Categoria> categoriasRaiz, String sortDir) {
		List<Categoria> categoriasJerarquicamente = new ArrayList<>();
		
		for (Categoria categoriaRaiz : categoriasRaiz) {
			categoriasJerarquicamente.add(Categoria.copiaCompleta(categoriaRaiz));
			
			Set<Categoria> hijos = sortSubCategorias(categoriaRaiz.getHijos(), sortDir);
			
			for (Categoria subCategoria : hijos) {
				String nombre = "--" + subCategoria.getNombre();
				categoriasJerarquicamente.add(Categoria.copiaCompleta(subCategoria, nombre));
				
				ListaSubCategoriasJerarquicamente(categoriasJerarquicamente, subCategoria, 1, sortDir);
			}
		}
		
		return categoriasJerarquicamente;
	}
	
	public void ListaSubCategoriasJerarquicamente(List<Categoria> categoriasJerarquicamente,
			Categoria padre, int subNivel, String sortDir) {
		Set<Categoria> hijos = sortSubCategorias(padre.getHijos(), sortDir);
		int nuevoSubNivel = subNivel + 1;
		
		for (Categoria subCategoria : hijos ) {
			String nombre = "";
			for(int i = 0; i < nuevoSubNivel; i++) {
				nombre += "--";
			}
			
			nombre += subCategoria.getNombre();
			
			categoriasJerarquicamente.add(Categoria.copiaCompleta(subCategoria, nombre));
			
			ListaSubCategoriasJerarquicamente(categoriasJerarquicamente, subCategoria, nuevoSubNivel, sortDir);
		}
	}
	
	public Categoria save(Categoria categoria) {
		return repo.save(categoria);
	}
	
	public List<Categoria> listaCategoriasUsadaEnForma() {
		List<Categoria> categoriasUsadasEnForma = new ArrayList<>();
		
		Iterable<Categoria> categoriasEnBD = repo.encuentraCategoriasRaiz(Sort.by("nombre").ascending());
		
		for (Categoria categoria : categoriasEnBD) {
			if (categoria.getPadre() == null) {
				categoriasUsadasEnForma.add(Categoria.copieIdYNombre(categoria));
				
				Set<Categoria> hijo = sortSubCategorias(categoria.getHijos()); 
				
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
		Set<Categoria> hijos = sortSubCategorias(padre.getHijos());  

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
	
	private SortedSet<Categoria> sortSubCategorias(Set<Categoria> hijos) {
		return sortSubCategorias(hijos, "asc");
	}
	
	private SortedSet<Categoria> sortSubCategorias(Set<Categoria> hijos, String sortDir) {
		SortedSet<Categoria> hijosOrdenados = new TreeSet<>(new Comparator<Categoria>() {		
			@Override
			public int compare(Categoria cat1, Categoria cat2) {
				if (sortDir.equals("asc")) {
					return cat1.getNombre().compareTo(cat2.getNombre());
				} else {
					return cat2.getNombre().compareTo(cat1.getNombre());
				}
			}
		});
		
		hijosOrdenados.addAll(hijos);
		
		return hijosOrdenados;
	}
	
	public void updateEstadoCategoria(Integer id, boolean activa) {
		repo.updateEstadoCategoria(id, activa);		
	}
	
	public void eliminar(Integer id) throws CategoriaNotFoundException {
		Long countById = repo.countById(id);
		if (countById == null || countById == 0) {
			throw new CategoriaNotFoundException ("No pude encontrar la categoría con ID " + id);
		}
		
		repo.deleteById(id);
	}
}
