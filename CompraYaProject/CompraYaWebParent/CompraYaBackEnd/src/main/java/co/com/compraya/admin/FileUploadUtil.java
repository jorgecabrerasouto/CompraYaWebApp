package co.com.compraya.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	
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
						System.out.println("Could not delete file: " + file);
					}
				}
			});
		} catch (IOException ex) {
			System.out.println("Could not list directory: " + dirPath);
		}
	}	
}
