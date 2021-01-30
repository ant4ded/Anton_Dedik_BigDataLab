package by.epam.data.analysis.crime.entity;

import lombok.Data;

import javax.persistence.*;

// TODO: 27.01.2021 4 entity

@Data
@Entity
@Table(name = "location")
public class CrimeLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private double latitude;
    @Column
    private double longitude;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "street_id", nullable = false)
    private Street street;
}
