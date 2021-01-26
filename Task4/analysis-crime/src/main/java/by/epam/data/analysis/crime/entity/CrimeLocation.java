package by.epam.data.analysis.crime.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "location", schema = "prod")
@Data
@NoArgsConstructor
public class CrimeLocation {
    @NonNull
    @Id
    @Column(name = "street_id")
    private Long streetId;
    @NonNull
    @Column(name = "street_name")
    private String streetName;
    @Column(name = "location_type")
    private String locationType;
    @Column(name = "location_subtype")
    private String locationSubtype;
    @NonNull
    @Column
    private double latitude;
    @NonNull
    @Column
    private double longitude;

    @OneToOne(mappedBy = "crimeLocation")
    private Crime crime;
}
