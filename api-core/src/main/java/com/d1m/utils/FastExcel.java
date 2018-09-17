package com.d1m.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

public class FastExcel {

    private Workbook workbook = null;
    private Sheet sheet = null;

    public FastExcel(String path) throws InvalidFormatException, IOException {
        File file = new File(path);
        workbook = WorkbookFactory.create(file);
        sheet = workbook.getSheetAt(0);
    }

    public FastExcel(InputStream is) throws InvalidFormatException, IOException {
        workbook = WorkbookFactory.create(is);
        sheet = workbook.getSheetAt(0);
    }

    public <T> List<T> praseExcel(Class<T> clazz) {
        List<T> rst = new ArrayList<>();
        if (sheet == null)
            return rst;
        int firstRowNum = sheet.getFirstRowNum();
        Row row = sheet.getRow(firstRowNum);
        short lastCellNum = row.getLastCellNum();

        // key:表头,value:对应的列数
        Map<String, Integer> cellNames = getCellMapping(row, lastCellNum);

        // key:映射的表头名字,value:对应的字段
        Map<String, Field> annotations = getFeildMapping(clazz);

        int lastRowNum = sheet.getLastRowNum();
        Set<String> keys = cellNames.keySet();
        try {
            for (int rowIndex = (++firstRowNum); rowIndex <= lastRowNum; rowIndex++) {
                T inst = clazz.newInstance();
                Row r = sheet.getRow(rowIndex);
                for (String key : keys) {
                    Field field = annotations.get(key);
                    if (field == null)
                        continue;
                    Integer col = cellNames.get(key);
                    Cell cel = r.getCell(col);
                    if (cel == null)
                        continue;
                    field.setAccessible(true);
                    cel.setCellType(Cell.CELL_TYPE_STRING);
                    String val = cel.getStringCellValue();
                    field.set(inst, val);
                }
                rst.add(inst);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return rst;
    }

    /**
     * 获取表头和列的映射关系
     *
     * @param row
     * @param lastCellNum
     * @return
     */
    private Map<String, Integer> getCellMapping(Row row, short lastCellNum) {
        // key:表头,value:对应的列数
        Map<String, Integer> cellNames = new HashMap<>();
        Cell cell;
        for (int col = 0; col < lastCellNum; col++) {
            cell = row.getCell(col);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            String val = cell.getStringCellValue();
            cellNames.put(val, col);
        }
        return cellNames;
    }

    /**
     * 获取对象字段和Excel表头的字段映射关联
     *
     * @param clazz
     * @return
     */
    private <T> Map<String, Field> getFeildMapping(Class<T> clazz) {
        // key:映射的表头名字,value:对应的字段
        Map<String, Field> annotations = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null || fields.length < 1)
            return annotations;
        for (Field field : fields) {
            CellMapping mapping = field.getAnnotation(CellMapping.class);
            if (mapping == null) {
                annotations.put(field.getName(), field);
            } else {
                annotations.put(mapping.cellName(), field);
            }
        }
        return annotations;
    }

}
