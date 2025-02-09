package com.snd.storefinder.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidStoreProductIdException extends RuntimeException {
  public InvalidStoreProductIdException(String message) {
    super(message);
  }
}
