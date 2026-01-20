package co.com.compraya.admin;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String nombreDirectorio = "foto-usuarios";
		Path trayectoriaDirectorioFotos = Paths.get(nombreDirectorio);
		
		String trayectoriaFotoUsuarios = trayectoriaDirectorioFotos.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/" + nombreDirectorio + "/**")
			.addResourceLocations("file:" + trayectoriaFotoUsuarios + "/");
		
		String nombreDirImagenesCategorias = "../imagenes-categorias";
		Path trayectoriaDirectorioImagenes = Paths.get(nombreDirImagenesCategorias);
		
		String trayectoriaImagenesCategoria = trayectoriaDirectorioImagenes.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/imagenes-categorias" + "/**")
			.addResourceLocations("file:" + trayectoriaImagenesCategoria + "/");
	
		String nombreDirLogosMarca = "../logos-marca";
		Path trayectoriaDirectorioLogos = Paths.get(nombreDirLogosMarca);
	
		String trayectoriaLogosMarca = trayectoriaDirectorioLogos.toFile().getAbsolutePath();
	
		registry.addResourceHandler("/logos-marca/**")
			.addResourceLocations("file:/" + trayectoriaLogosMarca + "/");	
	}
}
