package co.com.compraya.admin.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.compraya.common.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>, PagingAndSortingRepository<User, Integer>{
	@Query ("SELECT u FROM User u WHERE u.email = :email")
	public User getUsuarioByEmail(@Param("email") String email);
	
	public Long countById(Integer id);
	
	@Query ("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.primerNombre, ' ', u.primerApellido) LIKE %?1%")
	public Page<User> findAll(String textoBusqueda, Pageable pageable);
	
	@Query ("UPDATE User u SET u.activo =?2 WHERE u.id = ?1")
	@Modifying
	public void updateEstadoUsuario(Integer id, boolean activo);
	
}
