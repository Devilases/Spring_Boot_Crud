package zhelonin.hm3.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import zhelonin.hm3.dto.Response;
import zhelonin.hm3.exception.NotFoundException;

@ControllerAdvice
public class DefaultAdvice {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Response> handlerException(NotFoundException e){
    Response response = new Response(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
