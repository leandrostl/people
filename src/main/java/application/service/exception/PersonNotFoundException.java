package application.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PersonNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 570079750240454838L;

	public PersonNotFoundException(final String message) {
		super(message);
	}

}
