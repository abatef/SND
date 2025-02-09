package com.snd.storefinder.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidProductIdException extends RuntimeException {
    public InvalidProductIdException(String message) {
        super(message);
    }
}
