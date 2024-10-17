package com.rate_monitoring.rate_monitoring.dao;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;
import java.util.Objects;

public class excelSheetUpdate {
    XSSFWorkbook wb;
    XSSFSheet sheet1,sheet2;
    public String filePath;
    public excelSheetUpdate(String filePat) {
        try {
            FileInputStream fis = new FileInputStream(new File(filePat));
            wb = new XSSFWorkbook(fis);
            sheet1 = wb.getSheetAt(1);
            filePath = filePat;
        }catch (Exception e){
            System.out.println("Problem occurred in File Input Stream due to "+e);
        }
    }
    public void addSheet(List<Object> arr,String sheetName) {
        try {
        if (Objects.equals(sheetName, "Jumeirah")){sheet2=wb.getSheetAt(2);}
        else if (Objects.equals(sheetName, "iWTX")){sheet2=wb.getSheetAt(3);}
        else {sheet2=wb.getSheetAt(4);}
        int rowNo1=sheet1.getLastRowNum()+1;
        int rowNo2=sheet2.getLastRowNum()+1;
        XSSFRow row1 = sheet1.createRow(rowNo1);
        XSSFRow row2 = sheet2.createRow(rowNo2);
        XSSFCellStyle style = wb.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        for(int k=0;k<14;k++){
            Cell cell = row1.createCell(k);
            Cell cellno = row2.createCell(k);
            cell.setCellValue(arr.get(k).toString());
            cellno.setCellValue(arr.get(k).toString());
            cell.setCellStyle(style);
            cellno.setCellStyle(style);
        }

            FileOutputStream out = new FileOutputStream(new File(filePath));
            wb.write(out);
            out.close();
        }catch (Exception e){
            System.out.println("Problem occurred in File Output Stream due to "+e);
        }

    }
}
