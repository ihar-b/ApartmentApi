package Tests;



import org.junit.Before;
import org.junit.Test;

import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.writer.EntityWriter;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.Assert.assertFalse;

public class WriterTest {

    private static final String RESULT_FILENAME = "result.csv";


    @Before
    public void setUp() throws IOException {
        Files.deleteIfExists(Path.of(RESULT_FILENAME));
    }


    @Test
    public void testEmptyWrite() {
        EntityWriter.writeToCSV(null, RESULT_FILENAME);
        File file = new File(RESULT_FILENAME);
        assertFalse(file.exists());
    }
}

