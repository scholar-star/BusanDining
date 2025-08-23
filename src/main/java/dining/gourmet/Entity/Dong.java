package dining.gourmet.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="dong")
public class Dong {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "gu_id")
    private Gu guId;

    @Column()
    private String dongName;
}
