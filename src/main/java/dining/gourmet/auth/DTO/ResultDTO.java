package dining.gourmet.auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultDTO {
    private boolean success;
    private String message;
}
