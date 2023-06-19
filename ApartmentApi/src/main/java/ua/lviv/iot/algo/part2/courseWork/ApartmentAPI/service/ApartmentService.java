package ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.writer.EntityReader;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.writer.EntityWriter;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.writer.FilePathManager;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.models.Entity;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.models.Apartment;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.models.Room;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ApartmentService {

    private static final String PATH = "src/main/resources/entities/";
    private final Map<Class<? extends Entity>, List<Entity>> entitiesMap;
    private final AtomicInteger idCounter;

    @Autowired
    public ApartmentService() {
        this.entitiesMap = EntityReader.readEntities(PATH);
        this.idCounter = new AtomicInteger(EntityReader.getLastId(Apartment.class, PATH));
    }


    public List<? extends Entity> getApartments() {
        List<Entity> apartmentList = entitiesMap.getOrDefault(Apartment.class, new ArrayList<>());
        for (Entity entity : apartmentList) {
            if (entity instanceof Apartment apartment) {
                Set<Room> roomList = getRoomsForApartment(apartment);
                apartment.setRooms(roomList);
            }
        }
        return apartmentList;
    }

    private Set<Room> getRoomsForApartment(Apartment apartment) {
        Set<Room> roomList = new HashSet<>();
        List<Entity> roomEntities = entitiesMap.getOrDefault(Room.class, new ArrayList<>());
        for (Entity entity : roomEntities) {
            if (entity instanceof Room room && room.getApartmentId() == apartment.getId()) {
                roomList.add(room);
            }
        }
        return roomList;
    }

    public final Apartment createApartment(final Apartment apartment) {
        apartment.setId(idCounter.incrementAndGet());
        entitiesMap.computeIfAbsent(Apartment.class, k -> new LinkedList<>()).add(apartment);
        String path = FilePathManager.getFileName(apartment);
        EntityWriter.writeToCSV(apartment, path);
        return apartment;
    }

    public final Apartment getApartmentById(final Integer id) {
        return (Apartment) entitiesMap
                .getOrDefault(Apartment.class, new ArrayList<>())
                .stream()
                .filter(apartment -> apartment.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    public final Apartment updateApartment(final Integer id, final Apartment apartment) {
        Apartment apartmentFromDB = getApartmentById(id);
        if (apartmentFromDB != null) {
            apartment.setId(id);
            Set<Room> roomsToUpdate = apartmentFromDB.getRooms();
            for (Room room : roomsToUpdate) {
                room.setApartment(apartment);
            }
            entitiesMap.get(Apartment.class).remove(apartmentFromDB);
            entitiesMap.get(Apartment.class).add(apartment);
            EntityReader.updateEntityInCsv(apartment, PATH);
            for (Room room : roomsToUpdate) {
                EntityReader.updateEntityInCsv(room, PATH);
            }
            return apartment;
        } else {
            return null;
        }
    }

    public final boolean deleteApartment(final Integer id) {
        Apartment apartment = getApartmentById(id);
        if (apartment != null) {
            entitiesMap.get(Apartment.class).remove(apartment);
            EntityReader.deleteEntityFromCSV(apartment, PATH);

            apartment.getRooms().forEach(room -> {
                room.setApartment(null);
                room.setApartmentId(0);
                EntityReader.updateEntityInCsv(room, PATH);
            });

            return true;
        } else {
            return false;
        }
    }
}
