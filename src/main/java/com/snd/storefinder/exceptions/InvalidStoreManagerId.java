package com.snd.storefinder.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidStoreManagerId extends RuntimeException {
    public InvalidStoreManagerId(String message) {
        super(message);
    }
}
