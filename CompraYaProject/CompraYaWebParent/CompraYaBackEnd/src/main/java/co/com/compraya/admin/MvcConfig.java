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
	}

}
