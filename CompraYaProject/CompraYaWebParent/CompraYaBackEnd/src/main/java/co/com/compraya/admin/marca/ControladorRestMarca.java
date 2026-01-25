package co.com.compraya.admin.marca;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.compraya.common.entity.Categoria;
import co.com.compraya.common.entity.Marca;

@RestController
public class ControladorRestMarca {
	@Autowired
	private ServicioMarca servicio;
	
	@PostMapping("/marcas/chequear_unico")
	public String chequearUnico(@Param("id") Integer id, @Param("nombre") String nombre) {
		return servicio.chequearUnico(id, nombre);
	}
	
	@GetMapping("/marcas/{id}/categorias")
	public List<DTOCategoria> listarCategoriasPorMarca(@PathVariable(name ="id") Integer idMarca) throws MarcaNotFoundRestException {
		List<DTOCategoria> listaCategorias  = new ArrayList<>();
		
		try {
			Marca marca = servicio.get(idMarca);
			Set<Categoria> categorias = marca.getCategorias();
			
			for (Categoria categoria  : categorias) {
				DTOCategoria  dto = new DTOCategoria(categoria.getId(), categoria.getNombre());
				listaCategorias.add(dto);
			}
			
			return listaCategorias;
		} catch (MarcaNotFoundException e) {
			throw new MarcaNotFoundRestException();
		}
		
	}
}
