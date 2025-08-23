package dining.gourmet.repositories;

import dining.gourmet.Entity.Gu;
import org.springframework.stereotype.Repository;

@Repository
public interface GuRepository {
    public Gu findById(Integer id);
    public Gu findByName(String name);
}
