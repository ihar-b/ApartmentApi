package ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.writer.EntityReader;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.writer.FilePathManager;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.models.Entity;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.models.Apartment;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.models.Room;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.writer.EntityWriter;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RoomService {

    private static final String PATH = "src/main/resources/entities/";
    private final ApartmentService apartmentService;
    private final Map<Class<? extends Entity>, List<Entity>> entitiesMap;
    private final AtomicInteger idCounter;

    @Autowired
    public RoomService(final ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
        this.entitiesMap = EntityReader.readEntities(PATH);
        this.idCounter = new AtomicInteger(EntityReader.getLastId(Room.class, PATH));
    }


    public final List<? extends Entity> getRooms() {
        List<Entity> roomList = entitiesMap.getOrDefault(Room.class, new ArrayList<>());
        for (Entity entity : roomList) {
            if (entity instanceof Room room) {
                Apartment apartment = apartmentService.getApartmentById(room.getApartmentId());
                if (apartment != null) {
                    room.setApartment(apartment);
                }
            }
        }
        return roomList;
    }

    public final Room createRoom(final Room room) {
        room.setId(idCounter.incrementAndGet());
        entitiesMap.computeIfAbsent(Room.class, k -> new LinkedList<>()).add(room);
        String path = FilePathManager.getFileName(room);
        EntityWriter.writeToCSV(room, path);
        return room;
    }


    public final Room getRoomById(final Integer id) {
        return (Room) entitiesMap.getOrDefault(
                        Room.class, new ArrayList<>())
                .stream()
                .filter(room -> room.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public final Room updateRoom(final Integer id, final Room room) {
        Room roomFromDB = getRoomById(id);
        if (roomFromDB != null) {
            room.setId(id);
            entitiesMap.get(Room.class).remove(roomFromDB);
            entitiesMap.get(Room.class).add(room);

            Apartment apartmentFromDB = apartmentService.getApartmentById(roomFromDB.getApartmentId());
            Apartment newApartment = apartmentService.getApartmentById(room.getApartmentId());

            if (newApartment == null) {
                return null;
            }

            if (apartmentFromDB != null) {
                apartmentFromDB.getRooms().remove(roomFromDB);
            }

            newApartment.getRooms().add(room);

            room.setApartment(newApartment);
            room.setApartmentId(newApartment.getId());

            EntityReader.updateEntityInCsv(room, PATH);

            return room;
        } else {
            return null;
        }
    }

    public final boolean deleteRoom(final Integer id) {
        Room room = getRoomById(id);
        if (room != null) {
            entitiesMap.get(Room.class).remove(room);
            EntityReader.deleteEntityFromCSV(room, PATH);

            Apartment apartment = apartmentService.getApartmentById(room.getApartmentId());
            if (apartment != null) {
                apartment.getRooms().remove(room);
            }

            return true;
        } else {
            return false;
        }
    }
}
