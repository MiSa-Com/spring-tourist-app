package com.ms.tourist_app.config.exception;

import com.ms.tourist_app.adapter.web.base.RestData;
import com.ms.tourist_app.adapter.web.base.VsResponseUtil;
import com.ms.tourist_app.application.constants.UserMessageConstant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandlerConfig {

  private static final Log LOG = LogFactory.getLog(ExceptionHandlerConfig.class);

  private final MessageSource messageSource;

  public ExceptionHandlerConfig(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(value = {InvalidException.class})
  protected ResponseEntity<RestData<?>> handleVsException(InvalidException ex) {
    LOG.error(ex.getMessage(), ex);

    String message = messageSource.getMessage(ex.getMessage(), ex.getParams(), LocaleContextHolder.getLocale());
    return VsResponseUtil.error(HttpStatus.BAD_REQUEST, message, ex.getDevMessage());
  }

  @ExceptionHandler(value = {NotFoundException.class})
  protected ResponseEntity<RestData<?>> handleNotFoundException(NotFoundException ex) {
    LOG.error(ex.getMessage(), ex);

    String message = messageSource.getMessage(ex.getMessage(), ex.getParams(), LocaleContextHolder.getLocale());
    return VsResponseUtil.error(HttpStatus.NOT_FOUND, message, ex.getDevMessage());
  }

  @ExceptionHandler(value = {VsException.class})
  protected ResponseEntity<RestData<?>> handleVsException(VsException ex) {
    LOG.error(ex.getMessage(), ex);

    String message = messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
    return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, message, ex.getDevMessage());
  }

  @ExceptionHandler(value = {ConstraintViolationException.class})
  protected ResponseEntity<RestData<?>> handleConstraintViolationException(ConstraintViolationException ex) {
    LOG.error(ex.getMessage(), ex);

    Map<String, String> lsMessage = new LinkedHashMap<>();
    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
      String field = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
      String message;
      try {
        message = messageSource.getMessage(violation.getMessage(), null, LocaleContextHolder.getLocale());
      } catch (Exception e) {
        message = ex.getMessage();
      }
      lsMessage.put(field, message);
    }

    return VsResponseUtil.error(HttpStatus.BAD_REQUEST, lsMessage, ex.getCause() != null ?
        ex.getCause().getMessage() : ex.getMessage());
  }

  @ExceptionHandler(value = {BindException.class})
  protected ResponseEntity<RestData<?>> handleBindException(BindException ex) {
    LOG.error(ex.getMessage(), ex);

    Map<String, String> lsMessage = new LinkedHashMap<>();
    for (FieldError fieldError : ex.getFieldErrors()) {
      String field = fieldError.getField();
      String message;
      try {
        message = messageSource.getMessage(Objects.requireNonNull(fieldError.getDefaultMessage()), null,
            LocaleContextHolder.getLocale());
      } catch (Exception e) {
        message = ex.getMessage();
      }
      lsMessage.put(field, message);
    }

    return VsResponseUtil.error(HttpStatus.BAD_REQUEST, lsMessage, ex.getCause() != null ?
        ex.getCause().getMessage() : ex.getMessage());
  }

  @ExceptionHandler(value = {AccessDeniedException.class})
  protected ResponseEntity<RestData<?>> handleAccessDeniedException(Exception ex) {
    LOG.error(ex.getMessage(), ex);

    String message = messageSource.getMessage("err.exception.general", null, LocaleContextHolder.getLocale());
    return VsResponseUtil.error(HttpStatus.FORBIDDEN, message, ex.getCause() != null ?
        ex.getCause().getMessage() : ex.getMessage());
  }

  @ExceptionHandler(value = {Exception.class})
  protected ResponseEntity<RestData<?>> handleException(Exception ex) {
    LOG.error(ex.getMessage(), ex);

    if (ex.getCause() instanceof VsException) {
      VsException vsException = (VsException) ex.getCause();
      String message = messageSource.getMessage(vsException.getUserMessage(), vsException.getParams(),
          LocaleContextHolder.getLocale());
      return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, message, vsException.getDevMessage());
    }
    if (ex.getCause() instanceof InvalidException) {
      InvalidException invalidException = (InvalidException) ex.getCause();
      String message = messageSource.getMessage(invalidException.getUserMessage(), invalidException.getParams(),
          LocaleContextHolder.getLocale()
      );
      return VsResponseUtil.error(HttpStatus.BAD_REQUEST, message, invalidException.getDevMessage());
    }
    if (ex.getCause() instanceof NotFoundException) {
      NotFoundException invalidException = (NotFoundException) ex.getCause();
      String message = messageSource.getMessage(invalidException.getUserMessage(), invalidException.getParams(),
          LocaleContextHolder.getLocale()
      );
      return VsResponseUtil.error(HttpStatus.NOT_FOUND, message, invalidException.getDevMessage());
    }
    String message = messageSource.getMessage(UserMessageConstant.ERR_EXCEPTION_GENERAL, null,
        LocaleContextHolder.getLocale());
    return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, message, ex.getCause() != null ?
        ex.getCause().getMessage() : ex.getMessage());
  }

}
