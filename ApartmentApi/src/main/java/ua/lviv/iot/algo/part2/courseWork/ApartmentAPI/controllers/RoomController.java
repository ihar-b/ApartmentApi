package ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.models.Entity;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.models.Room;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.service.RoomService;

import java.util.List;


@RequestMapping("/rooms")
@RestController
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(final RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public final List<? extends Entity> getRooms() {
        return roomService.getRooms();
    }

    @GetMapping(path = "/{id}")
    public final Object getRoomById(final @PathVariable("id") Integer id) {
        return roomService.getRoomById(id) == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : roomService.getRoomById(id);
    }

    @PostMapping(produces = {"application/json"})
    public final Room createRoom(final @RequestBody Room room) {
        return roomService.createRoom(room);
    }

    @PutMapping(path = "/{id}")
    public final ResponseEntity<Room> updateRoom(final @PathVariable("id") Integer id, final @RequestBody Room room) {
        Room updatedRoom = roomService.updateRoom(id, room);
        return updatedRoom == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping(path = "/{id}")
    public final ResponseEntity<Void> deleteRoom(final @PathVariable("id") Integer id) {
        return roomService.deleteRoom(id) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
