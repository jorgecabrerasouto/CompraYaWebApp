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
@Table(name= "marcas")
public class Marca {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable=false, length = 45, unique = true)
	private String nombre;
	
	@Column(nullable=false, length = 128)
	private String logo;
	
	@ManyToMany
	@JoinTable(
			name = "marcas_categorias", 
			joinColumns = @JoinColumn(name = "id_marca"),
			inverseJoinColumns = @JoinColumn(name = "id_categoria")
			)
			
	private Set<Categoria> categorias = new HashSet<>();

	public Marca() {
		
	}
	
	public Marca(String nombre) {
		this.nombre = nombre;
		this.logo = "brand-logo.png";
	}
	
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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Set<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(Set<Categoria> categorias) {
		this.categorias = categorias;
	}

	@Override
	public String toString() {
		return "Marca [id=" + id + ", nombre=" + nombre + ", logo=" + logo + ", categorias=" + categorias + "]";
	}
	
	@Transient
	public String getTrayectoriaImagenLogo() {
		if (this.id == null || this.logo == null) return "/images/image-thumbnail.png";
		
		return "/logos-marca/" + this.id + '/' + this.logo;
	}
	
}
