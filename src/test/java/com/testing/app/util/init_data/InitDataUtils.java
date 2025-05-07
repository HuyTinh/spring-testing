package com.testing.app.util.init_data;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Stream;

public class InitDataUtils {

    private static final StringBuilder RESOURCE_PATH = new StringBuilder("src/test/resources/init_data_excel");

    public static Map<String, List<ValueWithValidity>> readValidationData(String filePath,
                                                                          String sheetName) throws Exception {
        Map<String, List<ValueWithValidity>> result = new LinkedHashMap<>();

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(RESOURCE_PATH.append("/").append(filePath)
                                                                                   .toString()))) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new IllegalArgumentException("Sheet '" + sheetName + "' not found");

            Row header = sheet.getRow(0); // first row has field names
            if (header == null) throw new IllegalArgumentException("Header row is empty");

            List<String> fields = new ArrayList<>();
            Map<String, Integer> valueColMap = new LinkedHashMap<>();
            Map<String, Integer> expectedColMap = new LinkedHashMap<>();
            Map<String, Integer> messageColMap = new LinkedHashMap<>();

            for (int col = 0; col < header.getLastCellNum(); col += 3) {
                String fieldName = getCellValue(header.getCell(col));
                if (fieldName == null || fieldName.isBlank()) continue;

                fields.add(fieldName);
                valueColMap.put(fieldName, col);
                expectedColMap.put(fieldName, col + 1);
                messageColMap.put(fieldName, col + 2);
                result.put(fieldName, new ArrayList<>());
            }

            for (int rowIdx = 1; rowIdx <= sheet.getLastRowNum(); rowIdx++) {
                Row row = sheet.getRow(rowIdx);
                if (row == null) continue;

                for (String field : fields) {
                    String value = getCellValue(row.getCell(valueColMap.get(field)));
                    String message = getCellValue(row.getCell(messageColMap.get(field)));
                    Boolean valid = getBooleanCellValue(row.getCell(expectedColMap.get(field)));

                    if (value != null && valid != null && message != null) {
                        result.get(field).add(new ValueWithValidity(value, message, valid));
                    }
                }
            }
        }

        return result;
    }

    private static String getCellValue(Cell cell) {
        return cell != null ? cell.toString().trim() : null;
    }

    private static Boolean getBooleanCellValue(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case BOOLEAN -> cell.getBooleanCellValue();
            case STRING -> {
                String val = cell.getStringCellValue().trim().toLowerCase();
                yield val.equals("true") || val.equals("1");
            }
            case NUMERIC -> cell.getNumericCellValue() != 0;
            default -> null;
        };
    }

    static public Stream<Map<String, ValueWithValidity>> provideData(Map<String, List<ValueWithValidity>> fieldValues) {
        List<String> keys = new ArrayList<>(fieldValues.keySet());

        Stream<Map<String, ValueWithValidity>> stream = Stream.of(new HashMap<>());

        for (String key : keys) {
            List<ValueWithValidity> values = fieldValues.get(key);
            stream = stream.flatMap(existingMap ->
                                            values.stream().map(value -> {
                                                Map<String, ValueWithValidity> newMap = new HashMap<>(existingMap);
                                                newMap.put(key, value);
                                                return newMap;
                                            })
                                   );
        }

        return stream;
    }
}
