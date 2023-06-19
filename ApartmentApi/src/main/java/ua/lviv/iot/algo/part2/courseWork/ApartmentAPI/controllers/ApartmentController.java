package ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.models.Entity;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.models.Apartment;
import ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.service.ApartmentService;

import java.util.LinkedList;
import java.util.List;

@RequestMapping("/apartments")
@RestController
public class ApartmentController {

    private final ApartmentService apartmentService;

    @Autowired
    public ApartmentController(final ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @GetMapping
    public final List<? extends Entity> getApartments() {
        return new LinkedList<>(apartmentService.getApartments());
    }

    @GetMapping(path = "/{id}")
    public final Object getApartmentById(final @PathVariable("id") Integer id) {
        return apartmentService.getApartmentById(id) == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : apartmentService.getApartmentById(id);
    }

    @PostMapping(produces = {"application/json"})
    public final Apartment createApartment(final @RequestBody Apartment apartment) {
        return apartmentService.createApartment(apartment);
    }

    @PutMapping(path = "/{id}")
    public final ResponseEntity<Apartment> updateApartment(final @PathVariable("id") Integer id, final @RequestBody Apartment apartment) {
        Apartment updatedApartment = apartmentService.updateApartment(id, apartment);
        return updatedApartment == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(updatedApartment);
    }

    @DeleteMapping(path = "/{id}")
    public final ResponseEntity<Void> deleteApartment(final @PathVariable("id") Integer id) {
        return apartmentService.deleteApartment(id) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
