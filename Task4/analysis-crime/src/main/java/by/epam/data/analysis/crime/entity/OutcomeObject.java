package by.epam.data.analysis.crime.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
public class OutcomeObject {
    @Id
    private String id;
    @Column
    private String name;
}
