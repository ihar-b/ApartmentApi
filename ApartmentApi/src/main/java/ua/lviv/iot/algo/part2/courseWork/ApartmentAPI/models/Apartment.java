package ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Apartment implements Entity {

    private static final String HEADERS = "id;city;street";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String street;
    private String city;
    private Integer id;

    @JsonIgnoreProperties("apartment")
    @OneToMany(mappedBy = "apartment", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Room> rooms = new HashSet<>();

    @JsonIgnore
    public final String getHeaders() {
        return HEADERS;
    }

    public final String toCSV() { return id+";" + city + ";" + street ;}
}
