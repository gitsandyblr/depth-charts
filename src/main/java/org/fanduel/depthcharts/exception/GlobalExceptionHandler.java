package org.fanduel.depthcharts.exception;

import lombok.extern.slf4j.Slf4j;
import org.fanduel.depthcharts.api.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public @ResponseBody ResponseDto handleRuntimeExceptions(RuntimeException ex, final HttpServletRequest request) {
    log.error("GlobalExceptionHandler : "+ex.getMessage());
    return ResponseDto.builder().message(ex.getMessage()).build();
  }

}
