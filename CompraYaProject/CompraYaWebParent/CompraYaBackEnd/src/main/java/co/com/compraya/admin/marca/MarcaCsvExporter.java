package co.com.compraya.admin.marca;

import java.io.IOException;
import java.util.List;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import co.com.compraya.admin.AbstractExporter;
import co.com.compraya.common.entity.Marca;
import jakarta.servlet.http.HttpServletResponse;

public class MarcaCsvExporter extends AbstractExporter {

	public void export (List<Marca> listaMarcas, HttpServletResponse response) 
			throws IOException {
		super.setResponseHeader(response, "text/csv", ".csv", "marcas_");
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), 
				CsvPreference.STANDARD_PREFERENCE);
		
		String[] csvHeader = {"Id Marca", "Nombre Marca"};
		String[] fieldMapping = {"id", "nombre"};
		
		csvWriter.writeHeader(csvHeader);
		
		for (Marca marca : listaMarcas) {
			marca.setNombre(marca.getNombre());
			csvWriter.write(marca, fieldMapping);
		}
		
		csvWriter.close();
	}
}

