package co.com.compraya.admin.marca;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.compraya.common.entity.Marca;

@Service
public class ServicioMarca {
	
	@Autowired
	private MarcaRepository repo;
	
	public List<Marca> listAll() {
		return (List<Marca>) repo.findAll();
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
			System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
			if(marcaByNombre != null) return "Duplicada";
		} else {
			System.out.println("ooooooooooooooooooooooooooooooooo");
			if(marcaByNombre != null && marcaByNombre.getId() != id) {
				return "Duplicada";
			}
		}
		System.out.println("ppppppppppppppppppppppppppp");
		return "OK";
	}
}
