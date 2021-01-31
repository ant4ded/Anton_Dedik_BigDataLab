package by.epam.data.analysis.crime.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "location")
public class CrimeLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private double latitude;
    @Column
    private double longitude;

    @JsonProperty("street")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "street_id", nullable = false)
    private Street street;
}
