package Tests;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.models.Apartment;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.models.Room;
import java.util.HashSet;


public class RoomTest {

    @Test
    public void testGetHeaders() {
        Room room = new Room();
        String expectedHeaders = "id;temperature;humidity;lighting;waterTemperature;batteryConsumption";
        String actualHeaders = room.getHeaders();

        Assertions.assertEquals(expectedHeaders, actualHeaders);
    }

    @Test
    public void testToCSV() {


        Room room = new Room(new Apartment(), 2, 4 , 5 ,6 ,7, 8, 4);
        room.setApartmentId(1);

        String expectedCSV = "4;5.0;6.0;7.0;8.0;4.0";
        String actualCSV = room.toCSV();

        Assertions.assertEquals(expectedCSV, actualCSV);
    }

    @Test
    public void testGetApartmentId() {
        Apartment apartment = new Apartment("dsfsd", "vdsg", 2, new HashSet<>());
        Room room = new Room();
        room.setApartment(apartment);

        Assertions.assertEquals(2, room.getApartmentId());
    }


    @Test
    public void testApartmentAssociation() {
        Apartment apartment = new Apartment("dsfsd", "vdsg", 2, new HashSet<>());
        Room room = new Room();
        room.setApartment(apartment);

        Assertions.assertEquals(apartment, room.getApartment());
    }
}
