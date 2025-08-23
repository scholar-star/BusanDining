package dining.gourmet.repositories;

import dining.gourmet.Entity.Dong;
import org.springframework.stereotype.Repository;

@Repository
public interface DongRepository {
    public Dong findById(Integer id);
    public Dong findByName(String name);
}
