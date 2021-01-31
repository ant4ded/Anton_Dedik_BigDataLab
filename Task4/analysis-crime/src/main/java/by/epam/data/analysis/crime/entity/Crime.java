package by.epam.data.analysis.crime.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("location_type")
    @Column(name = "location_type")
    private String locationType;
    @Column(name = "context")
    private String context;
    @JsonProperty("persistent_id")
    @Column(name = "persistent_id")
    private String persistentId;
    @JsonProperty("location_subtype")
    @Column(name = "location_subtype")
    private String locationSubtype;
    @Column(name = "month")
    private String month;

    @JsonProperty("outcome_status")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "outcome_status_id")
    private OutcomeStatus outcomeStatus;

    @JsonProperty("location")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private CrimeLocation crimeLocation;
}
