package com.kirill.idfc.services;

import com.kirill.idfc.entities.TaskEntity;
import com.kirill.idfc.entities.UserEntity;
import com.kirill.idfc.errors.BruhException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelGenerator {
    final static int maxWidth = 10000;
    static int sum = 0;
    static Row row;
    public static byte[] generateExcel(List<TaskEntity> tasks, List<TaskEntity> myTasks, List<TaskEntity> theirTasks, UserEntity user) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheetGeneral = workbook.createSheet("General");
        sheetBuilder(tasks, sheetGeneral);

        Sheet sheetYouReimbursed = workbook.createSheet("You reimbursed");
        sheetBuilder(myTasks, sheetYouReimbursed);

        Sheet sheetYouOwe = workbook.createSheet("You owe");
        sheetBuilder(theirTasks, sheetYouOwe);
        // потом доделать долги всех на general листе
        // хотя возможно хуйня
        // да, хуйня
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            throw new BruhException("Error during writing to byte stream: " + e.getMessage());
        }

        return outputStream.toByteArray();
    }

    private static void sheetBuilder(List<TaskEntity> tasks, Sheet sheetYouReimbursed) {
        Row headerRow;
        headerRow = sheetYouReimbursed.createRow(0);
        headerRow.createCell(0).setCellValue("Title");
        headerRow.createCell(1).setCellValue("Price, RUB");
        headerRow.createCell(2).setCellValue("Assigned");
        headerRow.createCell(3).setCellValue("Assigned Email");
        headerRow.createCell(4).setCellValue("Description");
        sum = 0;
        for (int i = 0; i < tasks.size(); i++) {
            row = sheetYouReimbursed.createRow(i+1);
            row.createCell(0).setCellValue(tasks.get(i).getTitle());
            row.createCell(1).setCellValue(tasks.get(i).getPrice());
            row.createCell(2).setCellValue(tasks.get(i).getUser().getName());
            row.createCell(3).setCellValue(tasks.get(i).getUser().getEmail());
            row.createCell(4).setCellValue(tasks.get(i).getDescription());
            sum += tasks.get(i).getPrice();
        }
        row = sheetYouReimbursed.createRow(tasks.size()+2);
        row.createCell(0).setCellValue("Summary");
        row.createCell(1).setCellValue(sum);
        for (int i = 0; i < 4; i++) {
            sheetYouReimbursed.autoSizeColumn(i);
            if (maxWidth < sheetYouReimbursed.getColumnWidth(i)) {
                sheetYouReimbursed.setColumnWidth(i, maxWidth);
            }
        }
    }
}
