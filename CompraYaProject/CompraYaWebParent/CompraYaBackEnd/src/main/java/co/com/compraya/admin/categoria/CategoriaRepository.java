package co.com.compraya.admin.categoria;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import co.com.compraya.common.entity.Categoria;

public interface CategoriaRepository extends CrudRepository<Categoria, Integer>, PagingAndSortingRepository<Categoria, Integer> {
	@Query("SELECT c FROM Categoria c WHERE c.padre is NULL")
	public List<Categoria> encuentraCategoriasRaiz(Sort sort);
	
	public Categoria findByNombre(String nombre);
	
	public Categoria findByAlias(String alias);
	
	@Query("UPDATE Categoria c SET c.activa = ?2 WHERE c.id = ?1")
	@Modifying
	public void updateEstadoCategoria (Integer id, boolean enabled);
}
