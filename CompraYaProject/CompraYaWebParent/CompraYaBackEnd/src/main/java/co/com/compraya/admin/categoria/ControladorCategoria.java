package co.com.compraya.admin.categoria;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import co.com.compraya.common.entity.Categoria;

@Controller
public class ControladorCategoria {
	
	@Autowired
	private ServicioCategoria service;
	
	@GetMapping("/categorias")
	public String listAll(Model model) {
		List<Categoria> listaCategorias = service.listAll();
		model.addAttribute("listaCategorias", listaCategorias);
		
		return "categorias/categorias";
	}
	
	@GetMapping("/categorias/nueva")
	public String CategoriaNueva(Model model) {
		List<Categoria> listaCategorias = service.listaCategoriasUsadaEnForma();
		
		model.addAttribute("categoria", new Categoria());
		model.addAttribute("listaCategorias", listaCategorias);
		model.addAttribute("pageTitle", "Crear una Categoría nueva");
		
		return "categorias/forma_categoria";
	}

}
