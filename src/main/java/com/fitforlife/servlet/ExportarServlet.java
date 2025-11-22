package com.fitforlife.servlet;

import com.fitforlife.dao.ProgresoDAO;
import com.fitforlife.dto.ProgresoDTO;
import com.fitforlife.dto.UsuarioDTO;
import com.fitforlife.util.ConexionBD;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

// Imports de Apache POI
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet("/ExportarServlet")
public class ExportarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        UsuarioDTO usuario = (UsuarioDTO) sesion.getAttribute("usuario");

        try (Connection conn = ConexionBD.getConnection()) {
            
            // 1. OBTENER LOS DATOS
            ProgresoDAO dao = new ProgresoDAO(conn);
            List<ProgresoDTO> listaProgreso = dao.listarProgresoPorUsuario(usuario.getIdUsuario());

            // 2. CREAR EL LIBRO DE EXCEL
            try (Workbook workbook = new XSSFWorkbook()) { 
                
                Sheet sheet = workbook.createSheet("Mi Progreso");
                // Definimos el ancho de las columnas
                sheet.setColumnWidth(0, 5000); // Fecha
                sheet.setColumnWidth(1, 2500); // Semana
                sheet.setColumnWidth(2, 5000); // Peso Registrado
                sheet.setColumnWidth(3, 5000); // Peso Meta

                // 3. CREAR ESTILOS
                Map<String, CellStyle> styles = createStyles(workbook);

                // 4. CREAR TÍTULO Y SUBTÍTULO
                // Fila de Título
                Row titleRow = sheet.createRow(0);
                titleRow.setHeightInPoints(45);
                Cell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("Reporte de Progreso - FitForLife");
                titleCell.setCellStyle(styles.get("title"));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3)); // Combinar 4 celdas para el título

                // Fila de Subtítulo
                Row subtitleRow = sheet.createRow(2);
                subtitleRow.setHeightInPoints(22);
                Cell subtitleCell = subtitleRow.createCell(0);
                subtitleCell.setCellValue("Usuario: " + usuario.getNombre());
                subtitleCell.setCellStyle(styles.get("subtitle"));
                sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 3)); // Combinar 4 celdas

                // 5. CREAR LA FILA DE CABECERA DE DATOS
                Row headerRow = sheet.createRow(4);
                headerRow.setHeightInPoints(25);
                String[] columnas = {"Fecha", "Semana", "Peso Registrado", "Peso Meta"};
                
                for (int i = 0; i < columnas.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columnas[i]);
                    cell.setCellStyle(styles.get("header"));
                }

                // 6. LLENAR EL EXCEL CON LOS DATOS
                int rowNum = 5;
                // La lista viene del más reciente al más antiguo, la invertimos para el Excel
                for (int i = listaProgreso.size() - 1; i >= 0; i--) {
                    ProgresoDTO progreso = listaProgreso.get(i);
                    Row row = sheet.createRow(rowNum++);
                    
                    Cell dateCell = row.createCell(0);
                    dateCell.setCellValue(progreso.getFecha());
                    dateCell.setCellStyle(styles.get("date"));

                    Cell weekCell = row.createCell(1);
                    weekCell.setCellValue("Semana " + progreso.getSemana());
                    weekCell.setCellStyle(styles.get("data"));

                    Cell weightCell = row.createCell(2);
                    weightCell.setCellValue(progreso.getPesoSemana());
                    weightCell.setCellStyle(styles.get("kg"));

                    Cell targetCell = row.createCell(3);
                    targetCell.setCellValue(progreso.getPesoMeta());
                    targetCell.setCellStyle(styles.get("kg"));
                }
                
                // 7. AÑADIR SECCIÓN DE RESUMEN
                if (!listaProgreso.isEmpty()) {
                    // Spacer row
                    rowNum++;
                    
                    ProgresoDTO primerRegistro = listaProgreso.get(listaProgreso.size() - 1);
                    ProgresoDTO ultimoRegistro = listaProgreso.get(0);
                    double cambio = ultimoRegistro.getPesoSemana() - primerRegistro.getPesoSemana();

                    Row summaryRow1 = sheet.createRow(rowNum++);
                    Cell labelCell1 = summaryRow1.createCell(2);
                    labelCell1.setCellValue("Peso Inicial:");
                    labelCell1.setCellStyle(styles.get("summary_label"));
                    Cell valueCell1 = summaryRow1.createCell(3);
                    valueCell1.setCellValue(primerRegistro.getPesoSemana());
                    valueCell1.setCellStyle(styles.get("summary_kg"));

                    Row summaryRow2 = sheet.createRow(rowNum++);
                    Cell labelCell2 = summaryRow2.createCell(2);
                    labelCell2.setCellValue("Peso Actual:");
                    labelCell2.setCellStyle(styles.get("summary_label"));
                    Cell valueCell2 = summaryRow2.createCell(3);
                    valueCell2.setCellValue(ultimoRegistro.getPesoSemana());
                    valueCell2.setCellStyle(styles.get("summary_kg"));

                    Row summaryRow3 = sheet.createRow(rowNum++);
                    Cell labelCell3 = summaryRow3.createCell(2);
                    labelCell3.setCellValue("Cambio Neto:");
                    labelCell3.setCellStyle(styles.get("summary_label"));
                    Cell valueCell3 = summaryRow3.createCell(3);
                    valueCell3.setCellValue(cambio);
                    valueCell3.setCellStyle(styles.get("summary_kg"));
                }


                // 8. PREPARAR LA RESPUESTA PARA DESCARGAR EL ARCHIVO
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setHeader("Content-Disposition", "attachment; filename=\"MiProgresoFitForLife.xlsx\"");

                // Escribir el libro de Excel en el OutputStream de la respuesta
                workbook.write(response.getOutputStream());
            }
            
        } catch (Exception e) {
            // Manejar cualquier excepción
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    /**
     * Método ayudante para crear todos los estilos de celda necesarios.
     */
    private Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<>();
        CreationHelper createHelper = wb.getCreationHelper();

        // Estilo de Título
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 22);
        titleFont.setBold(true);
        titleFont.setColor(IndexedColors.DARK_GREEN.getIndex());
        CellStyle titleStyle = wb.createCellStyle();
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        styles.put("title", titleStyle);

        // Estilo de Subtítulo
        Font subtitleFont = wb.createFont();
        subtitleFont.setFontHeightInPoints((short) 14);
        subtitleFont.setItalic(true);
        subtitleFont.setColor(IndexedColors.GREY_80_PERCENT.getIndex());
        CellStyle subtitleStyle = wb.createCellStyle();
        subtitleStyle.setFont(subtitleFont);
        subtitleStyle.setAlignment(HorizontalAlignment.CENTER);
        subtitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        styles.put("subtitle", subtitleStyle);

        // Estilo de Cabecera (Verde con texto blanco)
        Font headerFont = wb.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        CellStyle headerStyle = wb.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerStyle.setBorderTop(BorderStyle.MEDIUM);
        headerStyle.setBorderLeft(BorderStyle.MEDIUM);
        headerStyle.setBorderRight(BorderStyle.MEDIUM);
        styles.put("header", headerStyle);

        // Estilo de celda de datos genérica
        CellStyle dataStyle = wb.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        styles.put("data", dataStyle);
        
        // Estilo para celdas de Fecha
        CellStyle dateStyle = wb.createCellStyle();
        dateStyle.cloneStyleFrom(dataStyle);
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
        styles.put("date", dateStyle);

        // Estilo para celdas de Kilos
        CellStyle kgStyle = wb.createCellStyle();
        kgStyle.cloneStyleFrom(dataStyle);
        kgStyle.setDataFormat(createHelper.createDataFormat().getFormat("0.0\" kg\""));
        styles.put("kg", kgStyle);
        
        // Estilo para etiquetas de resumen
        Font summaryFont = wb.createFont();
        summaryFont.setBold(true);
        CellStyle summaryLabelStyle = wb.createCellStyle();
        summaryLabelStyle.setFont(summaryFont);
        summaryLabelStyle.setAlignment(HorizontalAlignment.RIGHT);
        styles.put("summary_label", summaryLabelStyle);
        
        // Estilo para valores de resumen
        CellStyle summaryKgStyle = wb.createCellStyle();
        summaryKgStyle.cloneStyleFrom(kgStyle);
        summaryKgStyle.setFont(summaryFont);
        summaryKgStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        summaryKgStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("summary_kg", summaryKgStyle);

        return styles;
    }
}