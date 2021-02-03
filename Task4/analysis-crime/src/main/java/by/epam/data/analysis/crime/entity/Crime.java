package by.epam.data.analysis.crime.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Entity
@Table
public class Crime {
    @Id
    private Long id;
    @Column
    private String category;
    @JsonProperty("location_type")
    @Column
    private String locationType;
    @Column
    private String context;
    @JsonProperty("persistent_id")
    @Column
    private String persistentId;
    @JsonProperty("location_subtype")
    @Column
    private String locationSubtype;
    @Column
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
