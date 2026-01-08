package co.com.compraya.common.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

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
	
	@ManyToOne
	@JoinColumn(name = "id_padre")
	private Categoria padre;
	
	@OneToMany(mappedBy="padre", cascade = CascadeType.ALL)
	private Set<Categoria> hijos = new HashSet<Categoria>();
	
	public Categoria() {
	}
	
	public Categoria(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	public Categoria(Integer id) {
		this.id = id;
	}

	public static Categoria copieIdYNombre(Categoria categoria) {
		Categoria copiaCategoria = new Categoria();
		copiaCategoria.setId(categoria.getId());
		copiaCategoria.setNombre(categoria.getNombre());
		
		return copiaCategoria;
	}

	public static Categoria copieIdYNombre(Integer id, String nombre) {
		Categoria copiaCategoria = new Categoria();
		copiaCategoria.setId(id);
		copiaCategoria.setNombre(nombre);
		
		return copiaCategoria;
	}
	
	public static Categoria copiaCompleta(Categoria categoria) {
		Categoria copiaCategoria = new Categoria();
		copiaCategoria.setId(categoria.getId());
		copiaCategoria.setNombre(categoria.getNombre());
		copiaCategoria.setImagen(categoria.getImagen());
		copiaCategoria.setAlias(categoria.getAlias());
		copiaCategoria.setActiva(categoria.isActiva());
		copiaCategoria.setTieneHijos(categoria.getHijos().size() > 0);
		
		return copiaCategoria;
	}
	
	public static Categoria copiaCompleta(Categoria categoria, String nombre) {
		Categoria copiaCategoria = Categoria.copiaCompleta(categoria);
		copiaCategoria.setNombre(nombre);
		return copiaCategoria;
	}
	
	public Categoria(String nombre) { 
		this.nombre = nombre;
		this.alias= nombre;
		this.imagen = "default.png";
	}
	
	public Categoria(String nombre, Categoria padre) {
		this(nombre);
		this.padre = padre;
	}	

	public Categoria(Integer id, String nombre, String alias) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.alias = alias;
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

	public Set<Categoria> getHijos() {
		return hijos;
	}

	public void setHijo(Set<Categoria> hijo) {
		this.hijos = hijo;
	}
	
	@Transient
	public String getTrayectoriaImagen() {
		if(this.id == null) return "/imagenes/image-thumbnail.png";
		
		return "/imagenes-categorias/" + this.id + "/" + this.imagen;
	}
	
	public boolean isTieneHijos() {
		return tieneHijos;
	}

	public void setTieneHijos(boolean tieneHijos) {
		this.tieneHijos = tieneHijos;
	}

	@Transient
	private boolean tieneHijos;
	
}
