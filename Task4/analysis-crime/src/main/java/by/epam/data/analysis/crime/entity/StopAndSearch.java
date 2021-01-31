package by.epam.data.analysis.crime.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "stop_and_search")
public class StopAndSearch {
    @Id
    private UUID id = UUID.randomUUID();
    @JsonProperty("age_range")
    @Column(name = "age_range")
    private String ageRange;
    @JsonProperty("involved_person")
    @Column(name = "involved_person")
    private boolean involvedPerson;
    @JsonProperty("self_defined_ethnicity")
    @Column(name = "self_defined_ethnicity")
    private String selfDefinedEthnicity;
    @Column
    private String legislation;
    @JsonProperty("outcome_linked_to_object_of_search")
    @Column(name = "outcome_linked_to_object_of_search")
    private boolean outcomeLinkedToObjectOfSearch;
    @Column
    private String datetime;
    @JsonProperty("removal_of_more_than_outer_clothing")
    @Column(name = "removal_of_more_than_outer_clothing")
    private boolean removalOfMoreThanOuterClothing;
    @Column
    private String operator;
    @JsonProperty("officer_defined_ethnicity")
    @Column(name = "officer_defined_ethnicity")
    private String officerDefinedEthnicity;
    @Column
    private String type;
    @JsonProperty("operation_name")
    @Column(name = "operation_name")
    private String operationName;
    @JsonProperty("object_of_search")
    @Column(name = "object_of_search")
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
