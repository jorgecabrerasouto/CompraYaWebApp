package co.com.compraya.admin.usuario;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import co.com.compraya.common.entity.Usuario;
import jakarta.servlet.http.HttpServletResponse;

public class UsuarioPdfExporter extends AbstractExporter {

	public void export(List<Usuario> listaUsuarios, HttpServletResponse respuesta) throws IOException {
		super.setResponseHeader(respuesta, "application/pdf", ".pdf");
		
		Document documento = new Document(PageSize.A4);
		PdfWriter.getInstance(documento, respuesta.getOutputStream());
		
		documento.open();
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);
		
		Paragraph parrafo = new Paragraph("Listado de Usuarios", font);
		parrafo.setAlignment(parrafo.ALIGN_CENTER);
		
		documento.add(parrafo);
		
		PdfPTable tabla = new PdfPTable(6);
		tabla.setWidthPercentage(100f);
		tabla.setSpacingBefore(10);
		tabla.setWidths(new float[] {1.2f, 3.5f, 3.0f, 3.0f, 3.0f, 1.7f });
		
		writeTableHeader(tabla);
		writeTableData(tabla, listaUsuarios);
		
		documento.add(tabla);
		
		documento.close();
	}

	private void writeTableData(PdfPTable tabla, List<Usuario> listaUsuarios) {
		for (Usuario usuario : listaUsuarios) {
			tabla.addCell(String.valueOf(usuario.getId()));
			tabla.addCell(String.valueOf(usuario.getEmail()));
			tabla.addCell(String.valueOf(usuario.getPrimerNombre()));
			tabla.addCell(String.valueOf(usuario.getPrimerApellido()));
			tabla.addCell(String.valueOf(usuario.getRoles().toString()));
			tabla.addCell(String.valueOf(usuario.isActivo()));
			
		}
	}

	private void writeTableHeader(PdfPTable tabla) {
		PdfPCell celda = new PdfPCell();
		celda.setBackgroundColor(Color.BLUE);
		celda.setPadding(5);
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setSize(12);
		font.setColor(Color.WHITE);	
		
		celda.setPhrase(new Phrase("ID", font));	
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("E-mail", font));	
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Primer Nombre", font));	
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Primer Apellido", font));	
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Roles", font));	
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Activo", font));	
		tabla.addCell(celda);
	}

}
