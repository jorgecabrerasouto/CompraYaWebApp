package co.com.compraya.common.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name= "categorias")
public class Categoria {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=128, nullable=false, unique=true)
	private String nombre;
	
	@Column(length=64, nullable=false, unique=true)
	private String alias;
	
	@Column(length=128, nullable=false)
	private String imagen;
	
	private boolean activa;
	
	@OneToOne
	@JoinColumn(name = "id_padre")
	private Categoria padre;
	
	@OneToMany(mappedBy = "padre")
	private Set<Categoria> hijo = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public Categoria getPadre() {
		return padre;
	}

	public void setPadre(Categoria padre) {
		this.padre = padre;
	}

	public Set<Categoria> getHijo() {
		return hijo;
	}

	public void setHijo(Set<Categoria> hijo) {
		this.hijo = hijo;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + ", alias=" + alias + ", imagen=" + imagen + ", activa="
				+ activa + ", padre=" + padre + ", hijo=" + hijo + "]";
	}
	
	
}
