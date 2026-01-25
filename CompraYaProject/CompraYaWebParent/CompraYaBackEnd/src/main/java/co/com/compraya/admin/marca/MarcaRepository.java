package co.com.compraya.admin.marca;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import co.com.compraya.common.entity.Marca;


public interface MarcaRepository extends CrudRepository<Marca, Integer>, PagingAndSortingRepository<Marca, Integer> {
	
	public long countById (Integer id);
	
	public Marca findByNombre(String nombre);
	
	@Query("SELECT m FROM Marca m WHERE m.nombre LIKE %?1%")
	public Page<Marca> findAll(String textoBusqueda, Pageable pageable);
	
	@Query("SELECT NEW Marca(m.id, m.nombre) FROM Marca m ORDER BY m.nombre ASC")
	public List<Marca> findAll();

}
