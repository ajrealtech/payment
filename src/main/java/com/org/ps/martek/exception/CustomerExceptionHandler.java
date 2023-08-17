package com.org.ps.martek.exception;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.org.ps.martek.service.ProductService;
@ControllerAdvice
public class CustomerExceptionHandler {

	Logger log = LoggerFactory.getLogger(ProductService.class);

	
	@ExceptionHandler({TimeoutException.class})
	public ResponseEntity<Object>  handleTimeoutException() {
		log.info("TimeOut: ");
		return new ResponseEntity<>("TimeOut in Service", HttpStatus.INTERNAL_SERVER_ERROR);

		
	}
	
	@ExceptionHandler(value = IOException.class)
	public ResponseEntity<Object> ioException(IOException exception) {
		 return new ResponseEntity<>("Problem in Service", HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ResponseEntity<Object> jdbcException(DataIntegrityViolationException exception) {
		 return new ResponseEntity<>("Validate Input", HttpStatus.BAD_REQUEST);

	}

	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> exception(Exception exception) {
		 return new ResponseEntity<>("Problem in Service", HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	
}
