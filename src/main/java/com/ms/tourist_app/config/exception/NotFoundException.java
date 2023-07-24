package com.ms.tourist_app.config.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class NotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private HttpStatus status;
  private String userMessage;
  private String devMessage;
  private String[] params;

  public NotFoundException(String userMessage, String devMessage) {
    super(userMessage);
    this.status = HttpStatus.NOT_FOUND;
    this.userMessage = userMessage;
    this.devMessage = devMessage;
  }

  public NotFoundException(HttpStatus status, String userMessage, String devMessage) {
    super(userMessage);
    this.status = status;
    this.userMessage = userMessage;
    this.devMessage = devMessage;
  }

  public NotFoundException(String userMessage, String devMessage, String[] params) {
    super(userMessage);
    this.userMessage = userMessage;
    this.devMessage = devMessage;
    this.params = params;
  }

}
