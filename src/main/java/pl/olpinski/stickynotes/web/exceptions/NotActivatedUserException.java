package pl.olpinski.stickynotes.web.exceptions;

import org.springframework.security.core.AuthenticationException;

public class NotActivatedUserException extends AuthenticationException {

    public NotActivatedUserException(String detail) {
        super(detail);
    }

    public NotActivatedUserException(String detail, Throwable ex) {
        super(detail, ex);
    }
}
