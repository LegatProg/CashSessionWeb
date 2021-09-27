package com.example.securingweb.file;

import com.example.securingweb.domain.Session;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class XlsWriter {
    public static void writeToXLS(File file, ArrayList<Session> sessionArrayList) throws IOException {

        Workbook book = new HSSFWorkbook();

        Sheet sheet = book.createSheet("Report");

        //Header
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("User");
        headerRow.createCell(2).setCellValue("Date");
        headerRow.createCell(3).setCellValue("Cash");
        headerRow.createCell(4).setCellValue("NonCash");
        headerRow.createCell(5).setCellValue("CashReturn");
        headerRow.createCell(6).setCellValue("NonCashReturn");
        headerRow.createCell(7).setCellValue("Cleaning");
        headerRow.createCell(8).setCellValue("Collection");
        headerRow.createCell(9).setCellValue("Terminal");
        headerRow.createCell(10).setCellValue("EndAmount");
        headerRow.createCell(11).setCellValue("Bonus");
        headerRow.createCell(12).setCellValue("Note");
        headerRow.createCell(13).setCellValue("Checked");

        //BODY
        for (int i = 0; i < sessionArrayList.size(); i++) {
            Session session = sessionArrayList.get(i);
            Row row = sheet.createRow(i + 1);

            row.createCell(0).setCellValue(session.getId());
            row.createCell(1).setCellValue(session.getUserName());

            Cell thisDate = row.createCell(2);
            DataFormat format = book.createDataFormat();
            CellStyle dateStyle = book.createCellStyle();
            dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));
            thisDate.setCellStyle(dateStyle);
            thisDate.setCellValue(session.getDate());

            row.createCell(3).setCellValue(session.getCash());
            row.createCell(4).setCellValue(session.getNonCash());
            row.createCell(5).setCellValue(session.getCashReturn());
            row.createCell(6).setCellValue(session.getNonCashReturn());
            row.createCell(7).setCellValue(session.getCleaning());
            row.createCell(8).setCellValue(session.getCollection());
            row.createCell(9).setCellValue(session.getTerminal());
            row.createCell(10).setCellValue(session.getEndAmount());
            row.createCell(11).setCellValue(session.getBonus());
            row.createCell(12).setCellValue(session.getNote());
            row.createCell(13).setCellValue(session.isChecked());
        }

        // Записываем всё в файл
        book.write(new FileOutputStream(file));
        book.close();
    }
}