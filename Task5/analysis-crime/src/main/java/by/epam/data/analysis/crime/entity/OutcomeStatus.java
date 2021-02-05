package by.epam.data.analysis.crime.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class OutcomeStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String category;
    @Column
    private String date;
}
