package com.snd.storefinder.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidUserIdentifierException extends RuntimeException {
    public InvalidUserIdentifierException(String message) {
        super(message);
    }

}
