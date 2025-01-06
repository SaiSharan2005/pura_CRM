// package com.crm.springbootjwtimplementation.exceptions;

// import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
// import org.slf4j.LoggerFactory;
// import org.slf4j.Logger;
// import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
// import org.springframework.context.annotation.PropertySource;
// import org.springframework.dao.DataIntegrityViolationException;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;
// import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// @RestControllerAdvice
// public class GlobalControllerExceptionHandler {
//     Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

//     // Exception Handler Here

//     // Exception Handler Here

//     // Exception Handler Here

//     @ExceptionHandler(DataIntegrityViolationException.class)
//     public ResponseEntity<Object> handleDataIntegrityException(Exception ex){
//         ApiError apiError = new ApiError.
//                 Builder()
//                 .withMessage("Validation Error")
//                 .withHttpStatus(HttpStatus.BAD_REQUEST)
//                 .withCreatedAt()
//                 .build();

//         return new ResponseEntity<>(apiError,apiError.getHttpStatus());

//     }
//     @ExceptionHandler(CustomSecurityException.class)
//     public ResponseEntity<Object> handleCustomSecurityException(CustomSecurityException ex){
//         ApiError apiError = new ApiError.
//                 Builder()
//                 .withMessage(ex.getMessage())
//                 .withHttpStatus(ex.getHttpStatus())
//                 .withCreatedAt()
//                 .build();

//         return new ResponseEntity<>(apiError,apiError.getHttpStatus());

//     }
//     @ExceptionHandler(Exception.class)
//     public ResponseEntity<Object> handleGenericException(Exception exception){

//         logger.error(exception.getMessage(),exception);

//         ApiError apiError = new ApiError.
//                 Builder()
//                 .withMessage("Some Error Occurred")
//                 .withHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//                 .withCreatedAt()
//                 .build();

//         return new ResponseEntity<>(apiError,apiError.getHttpStatus());

//     }
// }

package com.crm.springbootjwtimplementation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
       @ExceptionHandler(CustomSecurityException.class)
    public ResponseEntity<?> handleCustomSecurityException(CustomSecurityException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("status", ex.getHttpStatus().value()); // Numeric HTTP status
        errorDetails.put("error", ex.getHttpStatus().getReasonPhrase()); // Textual HTTP status

        return ResponseEntity.status(ex.getHttpStatus()).body(errorDetails);
    }

    // @ExceptionHandler(ValidationException.class)
    // public ResponseEntity<?> handleValidationException(ValidationException ex) {
    //     return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    // }
}
