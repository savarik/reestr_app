/*package parser;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class ExcelReader {
    private static final Map<String, String> HEADER_MAPPING = new LinkedHashMap<>();
    private static final Map<String, List<String>> FIELD_VARIANTS = new LinkedHashMap<>();

    private static final List<String> DB_FIELDS = Arrays.asList(
            "name", "okid2", "measurment", "price", "count", "sum",
            "debet", "credit", "country", "description", "sourse"
    );
    private static Set<Map<String, Object>> uniqueData = new LinkedHashSet<>();
    private static Set<Map<String, Object>> newRecords = new LinkedHashSet<>();
    private static final double SIMILARITY_THRESHOLD = 0.7;

    private static final String DB_URL = "jdbc:postgresql://localhost:5435/reestr";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    static {
        HEADER_MAPPING.put("Наименование", "name");
        HEADER_MAPPING.put("Наименование товара", "name");
        HEADER_MAPPING.put("Название", "name");
        HEADER_MAPPING.put("Код ОКПД 2", "okid2");
        HEADER_MAPPING.put("ОКПД2", "okid2");
        HEADER_MAPPING.put("Единица измерения", "measurment");
        HEADER_MAPPING.put("Ед. изм.", "measurment");
        HEADER_MAPPING.put("Цена", "price");
        HEADER_MAPPING.put("Цена за единицу", "price");
        HEADER_MAPPING.put("Кол-во", "count");
        HEADER_MAPPING.put("Количество", "count");
        HEADER_MAPPING.put("Сумма", "sum");
        HEADER_MAPPING.put("Дебет", "debet");
        HEADER_MAPPING.put("Кредит", "credit");
        HEADER_MAPPING.put("Страна", "country");
        HEADER_MAPPING.put("Страна происхождения", "country");
        HEADER_MAPPING.put("Описание", "description");
        HEADER_MAPPING.put("Характеристика", "description");
        HEADER_MAPPING.put("Источник", "sourse");
        HEADER_MAPPING.put("Файл", "sourse");

        FIELD_VARIANTS.put("name", Arrays.asList("наименование", "название", "товар", "продукт", "оборудование"));
        FIELD_VARIANTS.put("country", Arrays.asList("россия", "рф", "российская федерация", "китай", "кнр", "беларусь", "рб", "индия", "сша", "германия", "япония"));
    }

    public static void main(String[] args) {
        createTableIfNotExists();
        loadExistingDataFromDatabase();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Вводите пути к файлам");
        int fileCount = 0;
        int totalRows = 0;
        int duplicateCount = 0;

        while (true) {
            System.out.print("Файл " + (fileCount + 1) + ": ");
            String filePath = scanner.nextLine().trim();
            if (filePath.equalsIgnoreCase("стоп") || filePath.equalsIgnoreCase("stop")) {
                break;
            }
            if (filePath.isEmpty()) {
                System.out.println(" Путь пустой\n");
                continue;
            }
            int[] result = processFile(filePath);
            int rowsProcessed = result[0];
            int duplicates = result[1];
            if (rowsProcessed > 0) {
                fileCount++;
                totalRows += rowsProcessed;
                duplicateCount += duplicates;
                System.out.println("Обработано: (" + rowsProcessed + " новых строк, дубликатов: " + duplicates + ")\n");
            } else {
                System.out.println("Файл не удалось обработать\n");
            }
        }

        saveToDatabase();

        System.out.println(" Обработано файлов: " + fileCount);
        System.out.println(" Всего уникальных строк: " + uniqueData.size());
        System.out.println(" Отсечено дубликатов: " + duplicateCount);

        if (!uniqueData.isEmpty()) {
            System.out.println("\nИТОГ");
            System.out.print("Строка");
            for (String field : DB_FIELDS) {
                System.out.print(" | " + field);
            }
            System.out.println();
            System.out.println("-".repeat(60));
            int rowIndex = 1;
            for (Map<String, Object> row : uniqueData) {
                System.out.print(rowIndex);
                for (String field : DB_FIELDS) {
                    Object value = row.get(field);
                    String displayValue = (value != null) ? value.toString() : "-";
                    System.out.print(" | " + displayValue);
                }
                System.out.println();
                rowIndex++;
            }
        }
        scanner.close();
    }

    private static void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS equipment (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "okid2 VARCHAR(100), " +
                "measurment VARCHAR(50), " +
                "price DECIMAL(11,2), " +
                "count INTEGER, " +
                "sum INTEGER, " +
                "debet DECIMAL(12,2), " +
                "credit DECIMAL(12,2), " +
                "country VARCHAR(255), " +
                "description TEXT, " +
                "sourse VARCHAR(255)" +
                ")";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Таблица equipment готова");
        } catch (SQLException e) {
            System.err.println(" Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    private static void loadExistingDataFromDatabase() {
        String sql = "SELECT name, okid2, measurment, price, count, sum, debet, credit, country, description, sourse FROM equipment";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int loaded = 0;
            while (rs.next()) {
                Map<String, Object> record = new LinkedHashMap<>();
                record.put("name", rs.getString("name"));
                putIfNotNull(record, "okid2", rs.getString("okid2"));
                putIfNotNull(record, "measurment", rs.getString("measurment"));
                putIfNotNull(record, "price", rs.getBigDecimal("price"));
                putIfNotNull(record, "count", rs.getObject("count"));
                putIfNotNull(record, "sum", rs.getObject("sum"));
                putIfNotNull(record, "debet", rs.getBigDecimal("debet"));
                putIfNotNull(record, "credit", rs.getBigDecimal("credit"));
                putIfNotNull(record, "country", rs.getString("country"));
                putIfNotNull(record, "description", rs.getString("description"));
                putIfNotNull(record, "sourse", rs.getString("sourse"));
                uniqueData.add(record);
                loaded++;
            }
            System.out.println(" Загружено из БД: " + loaded + " существующих записей\n");

            if (loaded == 0) {
                System.out.println(" Таблица equipment пуста\n");
            }

        } catch (SQLException e) {
            System.err.println(" Ошибка загрузки данных из БД: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void putIfNotNull(Map<String, Object> map, String key, Object value) {
        if (value != null) {
            map.put(key, value);
        }
    }

    private static void saveToDatabase() {
        if (newRecords.isEmpty()) {
            System.out.println("Нет новых записей для сохранения");
            return;
        }

        String sql = "INSERT INTO equipment (name, okid2, measurment, price, count, sum, debet, credit, country, description, sourse) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);
            int savedCount = 0;

            for (Map<String, Object> record : newRecords) {
                pstmt.setString(1, (String) record.get("name"));
                pstmt.setString(2, (String) record.get("okid2"));
                pstmt.setString(3, (String) record.get("measurment"));
                pstmt.setBigDecimal(4, (BigDecimal) record.get("price"));

                Object countObj = record.get("count");
                if (countObj instanceof Integer) {
                    pstmt.setInt(5, (Integer) countObj);
                } else {
                    pstmt.setNull(5, Types.INTEGER);
                }

                Object sumObj = record.get("sum");
                if (sumObj instanceof Integer) {
                    pstmt.setInt(6, (Integer) sumObj);
                } else {
                    pstmt.setNull(6, Types.INTEGER);
                }

                pstmt.setBigDecimal(7, (BigDecimal) record.get("debet"));
                pstmt.setBigDecimal(8, (BigDecimal) record.get("credit"));
                pstmt.setString(9, (String) record.get("country"));
                pstmt.setString(10, (String) record.get("description"));
                pstmt.setString(11, (String) record.get("sourse"));
                pstmt.addBatch();
                savedCount++;
            }

            pstmt.executeBatch();
            conn.commit();
            System.out.println("\n Сохранено в БД: " + savedCount + " записей");

        } catch (SQLException e) {
            System.err.println(" Ошибка при сохранении в БД: " + e.getMessage());
        }
    }

    private static int[] processFile(String filePath) {
        int duplicates = 0;
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Workbook workbook;
            if (filePath.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (filePath.endsWith(".xls")) {
                workbook = new HSSFWorkbook(fis);
            } else {
                System.err.println("Ошибка: поддерживаются только .xls и .xlsx");
                return new int[]{0, 0};
            }
            Sheet sheet = workbook.getSheetAt(0);
            System.out.println(sheet.getSheetName());
            System.out.println(" ");

            Row headerRow = sheet.getRow(0);
            Map<Integer, String> columnToDbField = new HashMap<>();
            if (headerRow != null) {
                for (int i = 0; i < 20; i++) {
                    String excelHeader = getCellValue(headerRow.getCell(i));
                    if (excelHeader != null && !excelHeader.isEmpty()) {
                        String dbField = findMatchingDbField(excelHeader);
                        if (dbField != null) {
                            columnToDbField.put(i, dbField);
                            System.out.println("   Сопоставлено: '" + excelHeader + "' -> '" + dbField + "'");
                        } else {
                            System.out.println("  ️ Игнорируем столбец: '" + excelHeader + "'");
                        }
                    }
                }
            }

            if (columnToDbField.isEmpty()) {
                workbook.close();
                return new int[]{0, 0};
            }

            int rowNum = 0;
            int dataRows = 0;
            for (Row row : sheet) {
                rowNum++;
                if (rowNum == 1) continue;
                if (isRowEmpty(row)) continue;

                Map<String, Object> record = new LinkedHashMap<>();

                for (Map.Entry<Integer, String> entry : columnToDbField.entrySet()) {
                    int colIndex = entry.getKey();
                    String dbField = entry.getValue();
                    Cell cell = row.getCell(colIndex);
                    String value = getCellValue(cell);

                    if (value != null && !value.isEmpty()) {
                        String correctedValue = correctTypoInData(dbField, value);
                        if (!correctedValue.equals(value)) {
                            System.out.println("  Столбец " + (colIndex + 1) + ": '" + value + "' -> исправлено на '" + correctedValue + "'");
                        } else {
                            System.out.println("  Столбец " + (colIndex + 1) + ": " + value);
                        }
                        Object convertedValue = convertValueForDb(dbField, correctedValue);
                        if (convertedValue != null) {
                            record.put(dbField, convertedValue);
                        }
                    }
                }

                record.put("sourse", filePath.substring(filePath.lastIndexOf("\\") + 1));

                if (uniqueData.contains(record)) {
                    duplicates++;
                    System.out.println("  Дубликат, пропущен");
                } else {
                    uniqueData.add(record);
                    newRecords.add(record);
                    dataRows++;
                    System.out.println("  Новая запись добавлена");
                }
                System.out.println(" ");
            }
            workbook.close();
            return new int[]{dataRows, duplicates};
        } catch (Exception e) {
            System.err.println(" Ошибка при чтении файла: " + e.getMessage());
            e.printStackTrace();
            return new int[]{0, 0};
        }
    }

    private static String findMatchingDbField(String excelHeader) {
        if (HEADER_MAPPING.containsKey(excelHeader)) {
            return HEADER_MAPPING.get(excelHeader);
        }
        for (String key : HEADER_MAPPING.keySet()) {
            if (isSimilar(excelHeader, key, SIMILARITY_THRESHOLD)) {
                System.out.println("    Нечёткое совпадение заголовка: '" + excelHeader + "' -> '" + key + "'");
                return HEADER_MAPPING.get(key);
            }
        }
        return null;
    }

    private static String correctTypoInData(String dbField, String value) {
        if (dbField.equals("measurment")) {
            return value;
        }
        String lowerValue = value.toLowerCase().trim();
        if (FIELD_VARIANTS.containsKey(dbField)) {
            for (String variant : FIELD_VARIANTS.get(dbField)) {
                if (isSimilar(lowerValue, variant, SIMILARITY_THRESHOLD)) {
                    if (!lowerValue.equals(variant)) {
                        return variant;
                    }
                }
            }
        }
        return value;
    }

    private static boolean isSimilar(String str1, String str2, double threshold) {
        String s1 = str1.toLowerCase();
        String s2 = str2.toLowerCase();
        if (s1.equals(s2)) return true;
        if (s1.contains(s2) || s2.contains(s1)) return true;
        int distance = levenshteinDistance(s1, s2);
        int maxLen = Math.max(s1.length(), s2.length());
        double similarity = 1.0 - (double) distance / maxLen;
        return similarity >= threshold;
    }

    private static int levenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= s2.length(); j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
            }
        }
        return dp[s1.length()][s2.length()];
    }

    private static Object convertValueForDb(String dbField, String value) {
        try {
            switch (dbField) {
                case "price":
                case "debet":
                case "credit":
                    return new BigDecimal(value.replace(",", "."));
                case "count":
                case "sum":
                    return Integer.parseInt(value);
                default:
                    return value;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toLocalDate().toString();
                }
                double num = cell.getNumericCellValue();
                if (num == (long) num) {
                    return String.valueOf((long) num);
                }
                return String.valueOf(num);
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return String.valueOf(cell.getNumericCellValue());
                } catch (Exception e) {
                    return cell.getStringCellValue();
                }
            default:
                return null;
        }
    }

    private static boolean isRowEmpty(Row row) {
        if (row == null) return true;
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                String val = getCellValue(cell);
                if (val != null && !val.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
}*/