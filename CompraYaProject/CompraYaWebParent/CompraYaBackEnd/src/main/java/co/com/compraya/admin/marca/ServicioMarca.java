package co.com.compraya.admin.marca;

import java.util.List;

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
	

}
