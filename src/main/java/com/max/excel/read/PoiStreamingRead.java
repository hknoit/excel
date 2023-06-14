package com.max.excel.read;

import com.github.pjfanning.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PoiStreamingRead {

    public static void main(String[] args) {
        read();
    }

    private static void read() {
        Runtime runtime = Runtime.getRuntime();
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Current memory usage " + ((double) memory)/1024/1024 + "MB");
        long t = System.currentTimeMillis();
        Workbook workbook = StreamingReader.builder()
                .rowCacheSize(100)    // number of rows to keep in memory (defaults to 10)
                .bufferSize(4096)     // buffer size to use when reading InputStream to file (defaults to 1024)
                .open(new File("sample-data.xlsx"));            // InputStream or File for XLSX file (required)
        Sheet sheet = workbook.getSheet("Test");
        List<Object[]> list = new ArrayList<>();
        int maxMemIndex = 0;
        //int gcCount = 0;
        for (Row r : sheet) {
            Object[] arr = new Object[50];
            int index = 0;
            for (Cell c : r) {
                arr[index++] = c.getStringCellValue();
            }
            list.add(arr);
            long curMemory = runtime.totalMemory() - runtime.freeMemory();
            if (curMemory > memory) {
                memory = curMemory;
                maxMemIndex = r.getRowNum();
                //System.gc();
                //gcCount++;
            }
        }
        System.out.println("Max memory usage " + ((double) memory)/1024/1024 + "MB");
        System.out.println("Max memory usage at row " + maxMemIndex);
        //System.out.println("gc count " + gcCount);
        System.out.println("Time used: " + (System.currentTimeMillis() - t) + "ms");
    }

}
