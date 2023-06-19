package ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.writer;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.models.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FilePathManager {

    public static String getFileName(final Entity entity) {
        final String entityName = entity.getClass().getSimpleName().toLowerCase();
        return "src/main/resources/entities/" + entityName + "-" + getCurrentDate() + ".csv";
    }

    public static List<String> getFilesCreatedInThisMonth(final List<String> files) {
        String regex = "^\\w+-" + getCurrentMonth() + "-\\d{2}\\.csv$";
        Pattern pattern = Pattern.compile(regex);

        List<String> filteredFiles = new ArrayList<>();
        for (String file : files) {
            if (pattern.matcher(file).matches()) {
                filteredFiles.add(file);
            }
        }

        return filteredFiles;
    }

    public static String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }

    public static String getCurrentMonth() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
        return currentDate.format(formatter);
    }
}
