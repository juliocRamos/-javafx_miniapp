package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Map<String, String> issues = new HashMap<>();
	
	public ValidationException(String message) {
		super(message);
	}

	public Map<String, String> getIssues() {
		return issues;
	}

	public void setIssues(String fieldName, String errorMessage) {
		issues.put(fieldName, errorMessage);
	}
}
