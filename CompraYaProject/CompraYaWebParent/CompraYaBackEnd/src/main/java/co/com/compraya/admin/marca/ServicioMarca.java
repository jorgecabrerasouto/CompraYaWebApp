package co.com.compraya.admin.marca;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import co.com.compraya.common.entity.Marca;

@Service
public class ServicioMarca {
	public static final int MARCAS_POR_PAGINA = 10;
	
	@Autowired
	private MarcaRepository repo;
	
	public List<Marca> listAll() {
		return (List<Marca>) repo.findAll();
	}
	
	public Page<Marca> listByPage(int numPagina, String campoSort, String direccionSort, String textoBusqueda) {
		Sort sort = Sort.by(campoSort);
		
		sort = direccionSort.equals("asc") ? sort.ascending() : sort.descending();
				
		Pageable pageable = PageRequest.of(numPagina - 1, MARCAS_POR_PAGINA, sort);
		
		if (textoBusqueda != null) {
			return repo.findAll(textoBusqueda, pageable);
		}
		
		return repo.findAll(pageable);		
	}
	
	public Marca save(Marca marca) {
		return repo.save(marca);
	}
	
	public Marca get(Integer id) throws MarcaNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new MarcaNotFoundException ("No pude encontrar una Marca con ID " + id);
		}
	}
	
	public void eliminar(Integer id) throws MarcaNotFoundException {
		Long countById = repo.countById(id);
		
		if (countById == null || countById == 0) {
			throw new MarcaNotFoundException ("No pude encontrar una Marca con ID " + id);
		}
		
		repo.deleteById(id);
	}
	
	public String chequearUnico(Integer id, String nombre) {
		boolean estoyCreandoNuevo = (id == null || id == 0);
		Marca marcaByNombre = repo.findByNombre(nombre);
		
		if(estoyCreandoNuevo ) {
			if(marcaByNombre != null) return "Duplicada";
		} else {
			if(marcaByNombre != null && marcaByNombre.getId() != id) {
				return "Duplicada";
			}
		}

		return "OK";
	}
}
