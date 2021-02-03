package by.epam.data.analysis.crime.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table
public class StopAndSearch {
    @Id
    private UUID id = UUID.randomUUID();
    @JsonProperty("age_range")
    @Column
    private String ageRange;
    @JsonProperty("involved_person")
    @Column
    private boolean involvedPerson;
    @JsonProperty("self_defined_ethnicity")
    @Column
    private String selfDefinedEthnicity;
    @Column
    private String gender;
    @Column
    private String legislation;
    @JsonProperty("outcome_linked_to_object_of_search")
    @Column
    private boolean outcomeLinkedToObjectOfSearch;
    @Column
    private Timestamp datetime;
    @JsonProperty("removal_of_more_than_outer_clothing")
    @Column
    private boolean removalOfMoreThanOuterClothing;
    @Column
    private String operation;
    @JsonProperty("officer_defined_ethnicity")
    @Column
    private String officerDefinedEthnicity;
    @Column
    private String type;
    @JsonProperty("operation_name")
    @Column
    private String operationName;
    @JsonProperty("object_of_search")
    @Column
    private String objectOfSearch;

    @JsonProperty("outcome_object")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "outcome_object_id")
    private OutcomeObject outcomeObject;

    @JsonProperty("location")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private CrimeLocation crimeLocation;
}
