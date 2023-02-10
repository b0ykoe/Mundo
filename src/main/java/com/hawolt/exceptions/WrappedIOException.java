package com.hawolt.exceptions;

import java.io.IOException;

public class WrappedIOException extends RuntimeException {
    private final IOException origin;

    public WrappedIOException(IOException origin) {
        this.origin = origin;
    }

    public IOException unwrap() {
        return origin;
    }
}
