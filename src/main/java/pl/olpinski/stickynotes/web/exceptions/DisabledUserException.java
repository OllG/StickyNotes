package pl.olpinski.stickynotes.web.exceptions;

import org.springframework.security.core.AuthenticationException;

public class DisabledUserException extends AuthenticationException {

    public DisabledUserException(String msg, Throwable t) {
        super(msg, t);
    }

    public DisabledUserException(String msg) {
        super(msg);
    }
}
