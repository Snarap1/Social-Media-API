package sprng.boot.socialmediaapi.utils;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleException(IllegalArgumentException e){
    return ResponseEntity
            .badRequest()
            .body(e.getMessage());
}

@ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleException(BindException e){
    return ResponseEntity
            .badRequest()
            .body(e.getFieldError().getDefaultMessage());
}

}
