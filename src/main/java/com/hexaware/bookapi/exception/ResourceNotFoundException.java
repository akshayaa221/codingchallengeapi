package com.hexaware.bookapi.exception;

public class ResourceNotFoundException extends RuntimeException {
   
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String entity, String field, Object val) {
        super(entity + " not found with " + field + ": " + val);
    }
}
