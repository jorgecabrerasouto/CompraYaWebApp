package co.com.compraya.admin.usuario;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import co.com.compraya.common.entity.User;
import jakarta.servlet.http.HttpServletResponse;

public class UsuarioCsvExporter extends AbstractExporter {
	
	public void export(List<User> listaUsuarios, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "text/csv", ".csv");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String timestamp = dateFormatter.format(new Date());
		String fileName = "usuarios_" + timestamp + ".csv";
		
		response.setContentType("text/csv");
		
		System.out.println(fileName);
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName;
		response.setHeader(headerKey, headerValue);
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), 
				CsvPreference.STANDARD_PREFERENCE);
		
		String[] csvHeader = {"Id Usuario", "E-mail", "Primer Nombre", "Primer Apellido", "Roles", "Activo"};
		String[] fieldMapping = {"id", "email", "primerNombre", "primerApellido", "roles", "activo"};
		
		csvWriter.writeHeader(csvHeader);
		
		for (User user : listaUsuarios) {
			csvWriter.write(user, fieldMapping);
		}
		
		csvWriter.close();
	}

}
