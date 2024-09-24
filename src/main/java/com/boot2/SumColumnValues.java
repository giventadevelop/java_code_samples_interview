package com.boot2;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class SumColumnValues {

    public static void main(String[] args) {

//        String excelFilePath = "path/to/your/excel/file.xlsx";
        String excelFilePath = "E:/code_backup/vikas_sum.xlsx";
        int targetValue = 100;
        double sum = 0;

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);  // Assuming we are working with the first sheet
            for (Row row : sheet) {
                Cell cell = row.getCell(1);  // Column B (index 1)
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    if (cell.getNumericCellValue() == targetValue) {
                        sum += cell.getNumericCellValue();
                    }
                }
            }

            System.out.println("The sum of all values equal to 100 in column B is: " + sum);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
