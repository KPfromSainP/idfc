package com.kirill.idfc.services;

import com.kirill.idfc.entities.TaskEntity;
import com.kirill.idfc.errors.BruhException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelGenerator {
    public static byte[] generateExcel(List<TaskEntity> tasks) {
        
        for (TaskEntity task : tasks) {
            System.out.println("Task: " + task.getTitle());
        }
        
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Title");
        headerRow.createCell(1).setCellValue("Price");
        headerRow.createCell(2).setCellValue("Assigned");
        headerRow.createCell(3).setCellValue("Assigned Email");

        for (int i = 0; i < tasks.size(); i++) {
            Row row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(tasks.get(i).getTitle());
            row.createCell(1).setCellValue(tasks.get(i).getPrice());
            row.createCell(2).setCellValue(tasks.get(i).getUser().getName());
            row.createCell(3).setCellValue(tasks.get(i).getUser().getEmail());
        }
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            throw new BruhException("Error during writing to byte stream: " + e.getMessage());
        }

        return outputStream.toByteArray();
    }
}
