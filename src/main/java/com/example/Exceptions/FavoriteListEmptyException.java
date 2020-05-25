package com.example.Exceptions;

public class FavoriteListEmptyException extends Exception {

    public FavoriteListEmptyException() {
        super("Lista de favoritos vacia");
    }
}
