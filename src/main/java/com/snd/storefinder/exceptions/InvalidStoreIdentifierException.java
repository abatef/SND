package com.snd.storefinder.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidStoreIdentifierException extends RuntimeException {
    public InvalidStoreIdentifierException(String message) {
        super(message);
    }
}
