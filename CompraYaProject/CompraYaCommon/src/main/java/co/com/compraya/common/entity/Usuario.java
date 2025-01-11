package co.com.compraya.common.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=128, nullable=false, unique = true)
	private String email;
	
	@Column(length=64, nullable=false)
	private String password;
	
	@Column(name = "primer_nombre", length = 45, nullable=false)
	private String primerNombre;
	
	@Column(name = "primer_apellido", length = 45, nullable=false)
	private String primerApellido;
	
	@Column(length = 64)
	private String fotos;
	
	private boolean activo;
	
	@ManyToMany
	@JoinTable(
			name= "usuarios_roles",
			joinColumns = @JoinColumn(name = "usuario_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
			)
	private Set<Role> roles = new HashSet<>();
	
	public Usuario() {
	}

	public Usuario(String email, String password, String primerNombre, String primerApellido) {
		super();
		this.email = email;
		this.password = password;
		this.primerNombre = primerNombre;
		this.primerApellido = primerApellido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getFotos() {
		return fotos;
	}

	public void setFotos(String fotos) {
		this.fotos = fotos;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role) {
		this.roles.add(role);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", email=" + email + ", primerNombre=" + primerNombre + ", primerApellido="
				+ primerApellido + ", activo=" + activo + ", roles=" + roles + "]";
	}

	@Transient
	public String getTrayectoriaImagenFoto() {
		if (id == null || fotos == null) return "/images/default-user.png";
		
		return "/foto-usuarios/" + this.id + "/" + this.fotos;
	}
	
	@Transient
	public String getNombreCompleto() {
		return primerNombre + " " + primerApellido;
	}

}
