package by.epam.data.analysis.crime.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "outcome_status")
public class OutcomeStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "category")
    private String category;
    @Column(name = "date")
    private String date;
}
