package by.epam.data.analysis.crime.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "crime", schema = "prod")
@Data
@NoArgsConstructor
public class Crime {
    @NonNull
    @Id
    private Long id;
    @Column(name = "persistent_id")
    private String persistentId;
    @NonNull
    @Column(name = "category")
    private String category;
    @Column(name = "context")
    private String context;
    @NonNull
    @Column(name = "date")
    private String date;
    @Column(name = "outcome_status_date")
    private String outcomeStatusDate;
    @Column(name = "outcome_status_category")
    private String outcomeStatusCategory;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = CrimeLocation.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "street_id", referencedColumnName = "street_id")
    private CrimeLocation crimeLocation;
}
