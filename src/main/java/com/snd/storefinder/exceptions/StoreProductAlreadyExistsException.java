package com.snd.storefinder.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StoreProductAlreadyExistsException extends RuntimeException {
    public StoreProductAlreadyExistsException(String message) {
        super(message);
    }
}
