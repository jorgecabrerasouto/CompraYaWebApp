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
		
		exponerDirectorio("foto-usuarios", registry);
		exponerDirectorio("../imagenes-categorias", registry);
		exponerDirectorio("../logos-marca", registry);		
	}
		
		private void exponerDirectorio(String patronTrayectoria, ResourceHandlerRegistry registry) {
			Path trayectoria = Paths.get(patronTrayectoria);
			String trayectoriaAbsoluta = trayectoria.toFile().getAbsolutePath();
			
			String trayectoriaRelativa = patronTrayectoria.replace("../", "") + "/**";
								
			registry.addResourceHandler(trayectoriaRelativa)
			.addResourceLocations("file:/" + trayectoriaAbsoluta + "/");
	}
	
}
