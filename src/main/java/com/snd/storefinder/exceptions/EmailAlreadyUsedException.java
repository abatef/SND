package com.snd.storefinder.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
