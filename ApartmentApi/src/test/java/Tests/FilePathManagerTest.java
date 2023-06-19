package Tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.models.Apartment;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.writer.FilePathManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class FilePathManagerTest {

    @Test
    public void testGetFileName() {
        Apartment apartment = new Apartment("dsad", "fdsf", 2, new HashSet<>());
        String expectedFileName = "src/main/resources/entities/apartment-" + FilePathManager.getCurrentDate() + ".csv";
        String actualFileName = FilePathManager.getFileName(apartment);

        Assertions.assertEquals(expectedFileName, actualFileName);
    }


    @Test
    public void testGetFilesCreatedNotInThisMonth() {

        List<String> files = new ArrayList<>();
        files.add("apartment-2023-02-01.csv");
        files.add("apartment-2023-01-02.csv");
        files.add("apartment-2023-04-15.csv");

        List<String> expectedFiles = new ArrayList<>();

        Assertions.assertEquals(expectedFiles, FilePathManager.getFilesCreatedInThisMonth(files));
    }

    @Test
    public void testGetDate() {
        String expectedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String actualDate = FilePathManager.getCurrentDate();

        Assertions.assertEquals(expectedDate, actualDate);
    }

}
