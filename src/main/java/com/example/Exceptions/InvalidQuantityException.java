package com.example.Exceptions;

public class InvalidQuantityException extends Exception {

    public InvalidQuantityException() {
        super("Cantidad de resultados no valida");
    }
}
