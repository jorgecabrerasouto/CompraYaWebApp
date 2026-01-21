package co.com.compraya.admin.categoria;

import java.io.IOException;
import java.util.List;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import co.com.compraya.admin.AbstractExporter;
import co.com.compraya.common.entity.Categoria;
import jakarta.servlet.http.HttpServletResponse;

public class CategoriaCsvExporter extends AbstractExporter {	
	public void export (List<Categoria> listaCategorias, HttpServletResponse response) 
			throws IOException {
		super.setResponseHeader(response, "text/csv", ".csv", "categorias_");
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), 
				CsvPreference.STANDARD_PREFERENCE);
		
		String[] csvHeader = {"Id Categoria", "Nombre Categoria"};
		String[] fieldMapping = {"id", "nombre"};
		
		csvWriter.writeHeader(csvHeader);
		
		for (Categoria categoria : listaCategorias) {
			categoria.setNombre(categoria.getNombre().replace("--", "  "));
			csvWriter.write(categoria, fieldMapping);
		}
		
		csvWriter.close();
	}

}
