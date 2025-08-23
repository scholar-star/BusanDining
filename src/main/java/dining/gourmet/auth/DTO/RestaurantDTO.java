package dining.gourmet.auth.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Getter
@Setter
public class RestaurantDTO { // 응답
    private String name;
    private String gu;
    private String dong;
    private MultipartFile image;
    private List<String> popMenus;
    private String description;
    private Integer meanPrice;
    private int rate;
}
