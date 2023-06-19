package Tests;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.models.Apartment;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.models.Room;

import java.util.HashSet;
import java.util.Set;

public class ApartmentTest {

    @Test
    public void testGetHeaders() {
        Apartment apartment = new Apartment();
        String expectedHeaders = "id;city;street";
        String actualHeaders = apartment.getHeaders();

        Assertions.assertEquals(expectedHeaders, actualHeaders);
    }

    @Test
    public void testToCSV() {
        Apartment apartment = new Apartment("fds", "fdfssf", 1, new HashSet<>());
        String expectedCSV = "1;fdfssf;fds";
        String actualCSV = apartment.toCSV();

        Assertions.assertEquals(expectedCSV, actualCSV);
    }

    @Test
    public void testSettersAndGetters() {
        Apartment apartment = new Apartment();
        apartment.setId(1);
        apartment.setStreet("fvcx");
        apartment.setCity("qwer");

        Assertions.assertEquals(1, apartment.getId());
        Assertions.assertEquals("fvcx", apartment.getStreet());
        Assertions.assertEquals("qwer", apartment.getCity());
    }

    @Test
    public void testRooms() {
        Set<Room> rooms = new HashSet<>();
        Room room1 = new Room();
        Room room2 = new Room();

        rooms.add(room1);
        rooms.add(room2);

        Apartment apartment = new Apartment();
        apartment.setRooms(rooms);

        Assertions.assertEquals(rooms, apartment.getRooms());
    }
}
