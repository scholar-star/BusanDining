package dining.gourmet.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="gu")
public class Gu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String guName;
}
