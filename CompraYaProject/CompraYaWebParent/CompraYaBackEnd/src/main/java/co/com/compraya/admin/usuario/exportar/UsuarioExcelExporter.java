package co.com.compraya.admin.usuario.exportar;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import co.com.compraya.common.entity.User;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class UsuarioExcelExporter extends AbstractExporter {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	public UsuarioExcelExporter () {
		workbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLine () {
		sheet = workbook.createSheet("Usuarios");
		XSSFRow row = sheet.createRow(0);
		
		XSSFCellStyle cellStyle =workbook.createCellStyle();
		XSSFFont font =workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		cellStyle.setFont(font);
		
		createCell(row, 0, "Id Usuario", cellStyle);
		createCell(row, 1, "E-mail", cellStyle);
		createCell(row, 2, "Primer Nombre", cellStyle);
		createCell(row, 3, "Primer Apellido", cellStyle);
		createCell(row, 4, "Roles", cellStyle);
		createCell(row, 5, "Activo", cellStyle);
		
	}
	
	private void createCell (XSSFRow row, int columnIndex, Object value, CellStyle style) {
		XSSFCell cell =row.createCell(columnIndex);
		sheet.autoSizeColumn(columnIndex);
		
		if(value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);		
	}
	
	public void export(List<User> listaUsuarios, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "application/octet-stream", ".xlsx");
		
		writeHeaderLine();
		writeDataLines(listaUsuarios);
				
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
		
	}

	private void writeDataLines(List<User> listaUsuarios) {
		int rowIndex = 1;
		
		XSSFCellStyle cellStyle =workbook.createCellStyle();
		XSSFFont font =workbook.createFont();
		font.setFontHeight(14);
		cellStyle.setFont(font);
		
		for (User usuario : listaUsuarios) {
			XSSFRow row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
			
			createCell(row, columnIndex++, usuario.getId(), cellStyle);
			createCell(row, columnIndex++, usuario.getEmail(), cellStyle);
			createCell(row, columnIndex++, usuario.getPrimerNombre(), cellStyle);
			createCell(row, columnIndex++, usuario.getPrimerApellido(), cellStyle);
			createCell(row, columnIndex++, usuario.getRoles().toString(), cellStyle);
			createCell(row, columnIndex++, usuario.isActivo(), cellStyle);
			
		}
	}
}
