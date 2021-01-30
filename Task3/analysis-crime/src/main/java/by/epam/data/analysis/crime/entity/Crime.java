package by.epam.data.analysis.crime.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "crime")
public class Crime {
    @Id
    private Long id;
    @Column(name = "category")
    private String category;
    @Column(name = "location_type")
    private String locationType;
    @Column(name = "context")
    private String context;
    @Column(name = "persistent_id")
    private String persistentId;
    @Column(name = "location_subtype")
    private String locationSubtype;
    @Column(name = "month")
    private String month;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "outcome_status_id")
    private OutcomeStatus outcomeStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private CrimeLocation crimeLocation;
}
