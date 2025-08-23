package dining.gourmet.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table
public class Restaurants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String name;

    @OneToOne
    @JoinColumn(name="gu_id")
    Gu cityID;

    @OneToOne
    @JoinColumn(name="dong_id")
    Dong districtID;

    @Column(unique = true, nullable = false)
    Long mapx;

    @Column(unique = true, nullable = false)
    Long mapy;

    @Column(unique = true, nullable = false)
    String description;

    @Column(unique = true, nullable = false)
    @ColumnDefault("0")
    int rate;
}

