package dining.gourmet.auth.DTO;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
public class PostDTO { // insert 요청
    private String name;
    private String description;
    private String category;
    private List<String> menus;
    private int totalPrice;
    private int people;
    private MultipartFile image;
    private int rate;
}
