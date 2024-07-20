package Utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class UpdateExcel {
    static String filePath = "src/main/java/TestResult/TestResult.xlsx";

    public static void main(String[] args) {
        UpdateExcel.clearExistingData();
    }
    public static void updateExcel (int rowIndex, int ColumnIndex ,String Value )
    {

        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0);


            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                row = sheet.createRow(rowIndex);
            }
            Cell cell = row.getCell(ColumnIndex);
            if (cell == null) {
                cell = row.createCell(ColumnIndex);
            }

            // Set the Value
            cell.setCellValue(Value);

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     public static String ReadExcel (int rowIndex, int ColumnIndex )
      {
            String cellVal = "";
        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0);


            Row row = sheet.getRow(rowIndex);
            Cell cell = row.getCell(ColumnIndex);

            cellVal = cell.getStringCellValue();

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cellVal ;

    }

    public static int getLastRowEnabled()
    {
        int lastRow = 0;
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            // Find the last row with data
            lastRow = sheet.getLastRowNum();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastRow ;

    }

    public static void clearExistingData()
    {
        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = null;
            for(int i = 0; i<=sheet.getLastRowNum() ; i++)
            {
                row = sheet.getRow(i);
                if(row !=null)
                 sheet.removeRow(row);

            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
