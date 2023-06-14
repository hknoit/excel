package com.max.excel.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public class Test {
    // https://mvnrepository.com/artifact/org.apache.poi

    // https://mvnrepository.com/artifact/com.monitorjbl/xlsx-streamer
    // https://github.com/monitorjbl/excel-streaming-reader

    // https://mvnrepository.com/artifact/com.github.pjfanning/excel-streaming-reader
    // https://github.com/pjfanning/excel-streaming-reader

    // https://mvnrepository.com/artifact/org.dhatim/fastexcel
    // https://github.com/dhatim/fastexcel
    // https://github.com/dhatim/fastexcel/graphs/contributors
    // https://github.com/dhatim/fastexcel/commits?author=pjfanning
    // https://github.com/dhatim/fastexcel/commit/bfc03e9aab13d200d02806a20b1846df471aaf2a

    // https://github.com/openjdk/jmh

    public static void main(String[] args) throws IOException {
        write();
    }

    public static void write() throws IOException {
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        SXSSFSheet sheet = workbook.createSheet("Test");
        LocalDate localDate = LocalDate.parse("1970-01-01");
        sheet.trackAllColumnsForAutoSizing();
        for (int i = 0; i < 100000; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < 50; j++) {
                Cell cell = row.createCell(j);
                if (j % 3 == 0)
                    cell.setCellValue(localDate.plusDays(i).toString());
                if (j % 3 == 1)
                    cell.setCellValue(System.currentTimeMillis() + ".00");
                if (j % 3 == 2)
                    cell.setCellValue("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.".substring(0, 37) + " " + localDate.plusDays(i).toString() + " " +j);
            }
        }

        for (int i = 0; i < 50; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(new FileOutputStream("sample-data.xlsx"));
    }

}
