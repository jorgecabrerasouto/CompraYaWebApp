package co.com.compraya.admin.usuario;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.compraya.common.entity.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>, PagingAndSortingRepository<Usuario, Integer>{
	@Query ("SELECT u FROM Usuario u WHERE u.email = :email")
	public Usuario getUsuarioByEmail(@Param("email") String email);
	
	public Long countById(Integer id);
	
	@Query ("UPDATE Usuario u SET u.activo =?2 WHERE u.id = ?1")
	@Modifying
	public void updateEstadoUsuario(Integer id, boolean activo);
	
}
