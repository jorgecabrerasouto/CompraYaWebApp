package co.com.compraya.admin.marca;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import co.com.compraya.admin.categoria.ServicioCategoria;
import co.com.compraya.common.entity.Categoria;
import co.com.compraya.common.entity.Marca;

@Controller
public class ControladorMarca {

	@Autowired
	private ServicioMarca servicioMarca;
	
	@Autowired
	private ServicioCategoria servicioCategoria;
	
	@GetMapping("/marcas")
	public String listAll(Model model) {
		List<Marca> listaMarcas = servicioMarca.listAll();
		model.addAttribute("listaMarcas", listaMarcas);
		
		return "marcas/marcas";
	}
	
	
	@GetMapping("/marcas/nueva")
	public String nuevaMarca(Model model) {
		List<Categoria> listaCategorias = servicioCategoria.listaCategoriasUsadasEnForma();
		
		model.addAttribute("listaCategorias", listaCategorias);
		model.addAttribute("marca", new Marca());
		model.addAttribute("tituloPagina", "Cear una nueva Marca");
		
		return "marcas/forma_marcas";
	}
		
}
