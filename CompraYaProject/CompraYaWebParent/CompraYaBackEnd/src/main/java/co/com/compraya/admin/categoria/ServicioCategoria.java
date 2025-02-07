package co.com.compraya.admin.categoria;

import java.util.List;

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
}
