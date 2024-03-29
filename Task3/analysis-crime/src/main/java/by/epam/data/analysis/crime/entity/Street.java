package by.epam.data.analysis.crime.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "street")
public class Street {
    @Id
    private long id;
    @Column(name = "name")
    private String name;
}
