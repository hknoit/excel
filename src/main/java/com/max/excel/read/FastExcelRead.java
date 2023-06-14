package com.max.excel.read;

import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class FastExcelRead {
    public static void main(String[] args) {
        read();
    }

    private static void read() {
        Runtime runtime = Runtime.getRuntime();
        final Long[] memory = {runtime.totalMemory() - runtime.freeMemory()};
        System.out.println("Current memory usage " + ((double) memory[0])/1024/1024 + "MB");
        long t = System.currentTimeMillis();

        try (
                InputStream is = new FileInputStream("sample-data.xlsx");
                ReadableWorkbook wb = new ReadableWorkbook(is)
        ) {
            Sheet sheet = wb.getFirstSheet();
            final Integer[] maxMemIndex = {0};
            //final Integer[] gcCount = {0};
            try (Stream<Row> rows = sheet.openStream()) {
                List<Object[]> list = new ArrayList<>();
                rows.forEach(r -> {
                    Object[] arr = new Object[50];
                    for (int i = 0; i < 50; i++) {
                        arr[i] = r.getCell(i).getValue();
                    }
                    list.add(arr);
                    long curMemory = runtime.totalMemory() - runtime.freeMemory();
                    if (curMemory > memory[0]) {
                        memory[0] = curMemory;
                        maxMemIndex[0] = r.getRowNum();
                        //System.gc();
                        //gcCount[0]++;
                    }
                });
                System.out.println("Max memory usage " + ((double) memory[0])/1024/1024 + "MB");
                System.out.println("Max memory usage at row " + maxMemIndex[0]);
                //System.out.println("gc count " + gcCount[0]);
                System.out.println("Time used: " + (System.currentTimeMillis() - t) + "ms");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
