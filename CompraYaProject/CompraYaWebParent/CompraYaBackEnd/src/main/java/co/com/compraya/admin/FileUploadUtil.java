package co.com.compraya.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	private static final Logger LOGGER =LoggerFactory.getLogger(FileUploadUtil.class);
	
	public static void guardarArchivo (String directorioCargue, String nombreArchivo,
			MultipartFile multipartFile) throws IOException {
		
		Path trayectoriaCarga = Paths.get(directorioCargue);
		
		if(!Files.exists(trayectoriaCarga)) {
			Files.createDirectories(trayectoriaCarga);
		}
		
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path trayectoriaArchivo = trayectoriaCarga.resolve(nombreArchivo);
			Files.copy(inputStream, trayectoriaArchivo, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			throw new IOException("No se pudo guardar el archivo: " + nombreArchivo, ex); 
		}
	}

	public static void limpiarDirectorio(String dir) {
		Path dirPath = Paths.get(dir);
		
		try {
			Files.list(dirPath).forEach(file -> {
				if (!Files.isDirectory(file)) {
					try {
						Files.delete(file);
					} catch (IOException ex) {
						LOGGER.error("Could not delete file: " + file);
//						System.out.println("Could not delete file: " + file);
					}
				}
			});
		} catch (IOException ex) {
			LOGGER.error("Could not list directory: " + dirPath);
//			System.out.println("Could not list directory: " + dirPath);
		}
	}
	
	public static void eliminarDir(String dir) {
		limpiarDirectorio(dir);
		
		try {
			Files.delete(Paths.get(dir));
		} catch (IOException e) {
			LOGGER.error("No pude eliminar el directorio: " + dir);
		}
		
	}
}
