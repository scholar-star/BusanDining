package dining.gourmet.exceptions;


import ch.qos.logback.core.spi.ErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private ErrorCodes errorCode;
    private String message;
}
