package com.snd.storefinder.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidWishlistIdException extends RuntimeException {
    public InvalidWishlistIdException(String message) {
        super(message);
    }
}
