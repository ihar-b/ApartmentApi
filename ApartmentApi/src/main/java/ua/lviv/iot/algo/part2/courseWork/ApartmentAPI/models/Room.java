package ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Room implements Entity {

    private static final String HEADERS = "id;temperature;humidity;lighting;waterTemperature;batteryConsumption";

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("rooms")
    private Apartment apartment;

    @JsonIgnore
    private Integer apartmentId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double temperature;
    private double humidity;
    private double lighting;
    private double waterTemperature;
    private double batteryConsumption;
    public int getApartmentId() {
        return (apartmentId != null) ? apartmentId : (apartment != null ? apartment.getId() : 0);
    }

    @JsonIgnore
    public final String getHeaders() {
        return HEADERS;
    }

    public final String toCSV() {
        return id + ";" + temperature + ";" + humidity + ";" + lighting + ";" + waterTemperature + ";" + batteryConsumption;
    }
}
