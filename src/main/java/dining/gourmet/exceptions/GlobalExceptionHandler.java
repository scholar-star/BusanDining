package dining.gourmet.exceptions;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.rmi.ServerException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> UserNotFound(UsernameNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("사용자를 찾을 수 없습니다");
    }

    @ExceptionHandler(CustomServerException.class)
    public ResponseEntity<String> ServerException(CustomServerException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("서버 오류");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> BadCredentialsException(BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("잘못된 비밀번호입니다");
    }
}
