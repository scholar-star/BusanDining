package dining.gourmet.auth.service;

import dining.gourmet.Entity.Dong;
import dining.gourmet.Entity.Gu;
import dining.gourmet.repositories.DongRepository;
import dining.gourmet.repositories.GuRepository;
import lombok.Builder;
import org.springframework.stereotype.Service;

@Service
@Builder
public class AddressService {
    private GuRepository guRepository;
    private DongRepository dongRepository;

    public Gu guEntity(Integer id) {
        return guRepository.findById(id);
    }

    public Dong dongEntity(Integer id) {
        return dongRepository.findById(id);
    }
}
